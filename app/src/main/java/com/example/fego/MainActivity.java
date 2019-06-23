package com.example.fego;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;


public class MainActivity extends AppCompatActivity {
    private static final String  CLIENT_ID = "20f03825e6834755b19fd4e18d0c8faf";
    private static final String REDIRECT_URI = "https://fego.com/callback/";
    public static SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    @Override
    protected void onStart() {
        super.onStart();
        // Set the connection parameters
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();


        // Connect to App Remote
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.i("TAG", "Connected! Yay!");
                    }


                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("TAG", throwable.getMessage(), throwable);
                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
        Toast.makeText(this, "Connected to Spotify", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

    }



    @Override
    protected void onStop() {

        super.onStop();
        //Log.i("TAG", "App Stopped");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onPause() {
        super.onPause();
        //Log.i("TAG", "App Paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i("TAG", "App resumed");
    }

}

