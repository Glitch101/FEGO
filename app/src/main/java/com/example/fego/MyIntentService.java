package com.example.fego;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;




public class MyIntentService extends IntentService {




    public MyIntentService() {

        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("TAG","In MyIntentService");

        createNotificationChannel();
        showNotification();


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "fego client";
            String description = "Notification to keep service alive";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        // Launches service which connects to server
        Intent connectIntent = new Intent(this, serverService.class);
        connectIntent.putExtra("action", "connect");
        PendingIntent connectPendingIntent = PendingIntent.getService(this, 1, connectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Launches service which disconnects from server
        Intent disconnectIntent = new Intent(this, serverService.class);
        disconnectIntent.putExtra("action", "disconnect");
        PendingIntent disconnectPendingIntent = PendingIntent.getService(this, 2, disconnectIntent,PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("FEGO Client")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_action_connect, "Connect", connectPendingIntent)
                .addAction(R.drawable.ic_action_disconnect, "Disconnect", disconnectPendingIntent);

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(32, builder.build());
    }


}
