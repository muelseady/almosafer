package com.arts.m3droid.samatravel;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.arts.m3droid.samatravel.model.RequestingOfferDetails;
import com.arts.m3droid.samatravel.model.User;
import com.arts.m3droid.samatravel.ui.AuthUIActivity;
import com.arts.m3droid.samatravel.ui.userHistory.HistoryActivity;
import com.arts.m3droid.samatravel.utils.FirebaseFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import timber.log.Timber;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
        Timber.plant(new Timber.DebugTree());
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Timber.d("new offer " + remoteMessage.getData().get("userId"));
            Timber.d("key " + remoteMessage.getData().get("offerId"));
        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            //Because of the fact that @onMessageReceived will be triggered in case the app is in foreground
            //So showing the employee a notification is a must to be done manually

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Intent intent = new Intent(this, AuthUIActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            } else {
                Timber.d("User authorized " + remoteMessage.getData().get("body"));
                retrieveUser();
            }
        }

    }

    private void retrieveUser() {

        // Create an explicit intent for an Activity in your app


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        String userKey = currentUser.getUid();
        String userName = currentUser.getDisplayName();
        String userNumber = null;
        String userEmail = null;
        if (currentUser.getPhoneNumber() != null) {
            userNumber = currentUser.getPhoneNumber();
        }
        if (currentUser.getEmail() != null) {
            userEmail = currentUser.getEmail();
        }
        User user = new User(userKey, userName, userNumber, userEmail);


        Timber.d("Retrieved user " + user.getName());

        FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS).
                child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child(Constants.NODE_GOINGON_OFFERS).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (user.getGoinOnOffers() != null)
                            user.getGoinOnOffers().clear();
                        for (DataSnapshot goinOffersSnap : dataSnapshot.getChildren()) {
                            RequestingOfferDetails requestingOffer =
                                    goinOffersSnap.getValue(RequestingOfferDetails.class);
                            Objects.requireNonNull(requestingOffer).setOfferKey(goinOffersSnap.getKey());
                            user.setGoingOnOffers(requestingOffer);
                        }
                        createNotification(user, Objects.requireNonNull(userName));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void createNotification(User user, String userName) {

        if (userName.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName()))
            return;
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(Constants.NODE_USERS, user);
        Timber.d("offers " + user.getGoinOnOffers().size() + " name " + user.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "com.arts.m3droid.empl")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo))
                .setContentTitle("رساله جديده")
                .setContentText(userName)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1012, mBuilder.build());
    }


    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
