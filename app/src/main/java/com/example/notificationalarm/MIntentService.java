package com.example.notificationalarm;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Random;

public class MIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 3;

    public MIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ArrayList<String> arr = (ArrayList<String>) intent.getSerializableExtra("quotes");
        Random random = new Random();
        int max = arr.size();
        int min = 0;
        int randomNumber = random.nextInt(max-min) + min;
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Take it Slow")
                .setContentText(arr.get(randomNumber))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
