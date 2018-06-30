package com.arts.m3droid.samatravel;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import timber.log.Timber;

public class CurrentFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Timber.d("Refreshed token: " + refreshedToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }
}
