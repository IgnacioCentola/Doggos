package com.nacho.dogsapp.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.view.MainActivity;

public class NotificationsHelper {
    private static NotificationsHelper instance;
    private final Context context;

    private static final String CHANNEL_ID = "DOG_CHANNEL_ID";
    private static final int NOTIFICATION_ID = 123;

    private NotificationsHelper(Context context) {
        this.context = context;
    }

    public static NotificationsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationsHelper(context);
        }
        return instance;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification() {

        createNotificationChannel();

        //* Define user tapping on the notification action
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog_1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog_1)
                .setContentTitle("Dogs retrieved from endpoint successfully!")
                .setLargeIcon(largeIcon)
                .setContentText("Dogs retrieved")
                .setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigLargeIcon(largeIcon)
                                .bigPicture(largeIcon))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(context.getColor(R.color.primaryLightColor));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
