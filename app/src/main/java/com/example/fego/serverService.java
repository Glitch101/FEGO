package com.example.fego;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.fego.MainActivity.mSpotifyAppRemote;


public class serverService extends IntentService {


    public static int b = 0;
    public static Socket s;




    public serverService() {
        super("serverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        createNotificationChannel();
        showNotification();
        String action = intent.getStringExtra("action");
        if(action.equals("connect")) {
            // Connect to server
            Log.i("TAG", " Connecting to server");
            connectServer();
        } else if(action.equals("disconnect")) {
            // Disconnect from server
            disconnectServer();
        }
    }

    protected void connectServer() {
        // Connect to server when user taps connect button
        try {
            s = new Socket("192.168.43.216",8000);
        } catch(IOException exc) {
            Log.i("TAG", "Error connecting to server");
        }
        send sendcode = new send();
        sendcode.execute();

    }

    protected void disconnectServer() {
        // Disconnect from server when user taps disconnect button
        Log.i("TAG", " in Disconnect Server");
        try {
            // Send message to server to close socket and start again
            DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
            outToServer.writeBytes("s");

            s.shutdownInput();
            s.close();
        } catch(IOException exc) {
            Log.i("TAG", "Error closing socket");
        }

    }


    private void connected() {
        Log.i("TAG", "In connected");

        //Read instructions from server

        if(b == 1){
            // Resume
            Log.i("TAG", "in b  == 1");

            mSpotifyAppRemote.getPlayerApi().resume();

        } else if(b == 2) {
            // Pause
            Log.i("TAG", "in b  == 2");
            mSpotifyAppRemote.getPlayerApi().pause();

        } else if(b == 3) {
            // Play playlist 1
            Log.i("TAG", "in b  == 3");
            mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX3Ogo9pFvBkY"); // Playlist number 1


        } else if(b == 4) {
            // Play playlist 2
            Log.i("TAG", "in b  == 4");
            mSpotifyAppRemote.getPlayerApi().play("spotify:album:5Gf5m9M6RiK2lkjpbP0xRu"); // Playlist number 2


        }


        // Subscribe to PlayerState
        //mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(playerState -> {final Track track = playerState.track;if(track!=null) {Log.d("MainActivity", track.name + " by " + track.artist.name);}});
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Server";
            String description = "Connected";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("2", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("FEGO")
                .setContentText("Connected to Server")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Connected"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(33, builder.build());
    }

    class send extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void...params){
            try {

                Log.i("TAG", "Connected to Server");
                //DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
                int a;
                while(true) {
                    // When socket closed, break out of loop

                    Log.i("TAG", "Waiting for instruction");
                    a = inFromServer.read();
                    b = Character.getNumericValue(a);
                    Log.i("TAG", "Got code. Code is : " + b);
                    if(b == 1 | b == 2 | b == 3 | b == 4) {
                        Log.i("TAG", "calling connected()");
                        connected();
                    }

                } //Log.i("TAG", "Socket closed and outside while baby");

            } catch (UnknownHostException e) {

                Log.i("TAG", "UNKNOWNHOSTEXCEPTION in closing socket");
                e.printStackTrace();
            } catch (IOException e) {
                // When socket is closed, this exception is thrown by

                Log.i("TAG", "IOEXCEPTION in closing socket");
                e.printStackTrace();
            }
            return null;
        }
    }

}
