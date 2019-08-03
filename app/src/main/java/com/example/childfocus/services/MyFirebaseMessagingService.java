package com.example.childfocus.services;

import android.util.Log;

import com.example.childfocus.ui.login.UserToken;
import com.example.childfocus.utils.HttpUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Collections;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CLASS = MyFirebaseMessagingService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(CLASS, remoteMessage.getData().toString());
    }

    @Override
    public void onNewToken(String firebaseToken) {
        Log.d(CLASS, "Refreshed token: " + firebaseToken);

        HttpUtils.post(
                "api/missions/subscribe",
                UserToken.getInstance().getToken(),
                Collections.singletonList(firebaseToken),
                HttpUtils.dumbResponseHandler()
        );
    }
}
