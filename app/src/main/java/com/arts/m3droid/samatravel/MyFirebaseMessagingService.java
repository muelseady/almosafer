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
import com.arts.m3droid.samatravel.ui.userHistory.historyDetails.HistoryOfferDetails;
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


            //Because of the fact that @onMessageReceived will be triggered in case the app is in foreground
            //So showing the employee a notification is a must to be done manually

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                Intent intent = new Intent(this, AuthUIActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            } else {
                retrieveUser(remoteMessage.getData().get("userId"), remoteMessage.getData().get("offerId"), remoteMessage.getData().get("senderName"));
            }
        }


    }

    private void retrieveUser(String userID, String offerID, String senderName) {
        FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS)
                .child(userID)
                .child(Constants.NODE_GOINGON_OFFERS)
                .child(offerID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        RequestingOfferDetails requestingOfferDetails =
                                dataSnapshot.getValue(RequestingOfferDetails.class);
                        Objects.requireNonNull(requestingOfferDetails).setOfferKey(dataSnapshot.getKey());

                        FirebaseFactory.getDatabase().getReference(Constants.NODE_USERS)
                                .child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);

                                createNotification(user, requestingOfferDetails, senderName);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void createNotification(User user, RequestingOfferDetails offerDetails, String senderName) {

        Intent intent = new Intent(this, HistoryOfferDetails.class);

        intent.putExtra(Constants.NODE_USERS, user);
        intent.putExtra(Constants.NODE_GOINGON_OFFERS, offerDetails);
        intent.setAction("id");

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "com.arts.m3droid.empl")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo))
                .setSmallIcon(R.drawable.notification_logo)
                .setContentTitle("رساله جديده")
                .setContentText(" من " + senderName)
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
