package com.example.techie_desi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.techie_desi.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }
    public void showNotification (String message){
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);

                Notification notification = new NotificationCompat.Builder(this,"M_CH_ID")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Techie Desi")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0,notification);
    }
}
