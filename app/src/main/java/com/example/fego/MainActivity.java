package com.example.fego;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;


public class MainActivity extends AppCompatActivity {


    private UriViewModel mUriViewModel;

    private static final String  CLIENT_ID = ""; // your client id
    private static final String REDIRECT_URI = ""; // your redirect uri
    public static SpotifyAppRemote mSpotifyAppRemote;
    EditText mplayList1;
    EditText mplayList2;
    EditText mblacklist;
    Button save1;
    Button save2;
    public static String playlist_1_uri = "spotify:user:spotify:playlist:37i9dQZF1DX3Ogo9pFvBkY";
    public static String playlist_2_uri = "spotify:album:5Gf5m9M6RiK2lkjpbP0xRu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUriViewModel = ViewModelProviders.of(this).get(UriViewModel.class);

    }

    public void loadData() {
        //get playlists uri
        SharedPreferences prefs= this.getSharedPreferences("playlist_uri" , MainActivity.MODE_PRIVATE);
        // Playlist 1
        playlist_1_uri = prefs.getString("uri1",playlist_1_uri);
        // Playlist 2
        playlist_2_uri = prefs.getString("uri2",playlist_2_uri);
    }

    public void saveData() {
        // Save both playlists uri
        SharedPreferences prefs = this.getSharedPreferences("playlist_uri" , MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("uri1" , playlist_1_uri);
        editor.putString("uri2", playlist_2_uri);
        editor.apply();
    }

    public void add(View view) {
        Boolean z;
      mblacklist = findViewById(R.id.blacklist_textview);
       if(!mblacklist.getText().toString().equals("")) {
            Uri uri = new Uri(mblacklist.getText().toString());
           z = mUriViewModel.insert(uri);
           if(z == false) {
               Toast.makeText(this, "URI already in blacklist", Toast.LENGTH_LONG).show();
           } else Toast.makeText(this, "Added to blacklist", Toast.LENGTH_LONG).show();
        } else {
           Log.i("TAGG", " in else");
            Toast.makeText(MainActivity.this, "Please paste URI first", Toast.LENGTH_SHORT).show();
        }

    }

    public void remove(View view) {
        Boolean z;
        mblacklist = findViewById(R.id.blacklist_textview);
        if(!mblacklist.getText().toString().equals("")) {
            Uri uri = new Uri(mblacklist.getText().toString());
            z = mUriViewModel.delete(uri);
            if(z == false) {
                Toast.makeText(this, "URI not found", Toast.LENGTH_LONG).show();
            } else Toast.makeText(this, "Deleted successfully", Toast.LENGTH_LONG).show();
        } else {
            Log.i("TAGG", " in else");
            Toast.makeText(MainActivity.this, "Please paste URI first", Toast.LENGTH_SHORT).show();
        }

    }

    public void  updatePlaylistUri(View v) {

        mplayList1 = (EditText)findViewById(R.id.playlist1_textview);
        mplayList2 = (EditText)findViewById(R.id.playlist2_textview);
        // Determine which button was pressed and then accordingly update
        // that playlist's URI

        // Determine which button was pressed
        int id = v.getId();
        switch(id) {
            case R.id.playlist1_button:
                if(!mplayList1.getText().toString().equals("")) {
                    playlist_1_uri = mplayList1.getText().toString();
                    Toast.makeText(this, "Playlist 1 updated", Toast.LENGTH_LONG).show();
                }
                copyToClipboard(playlist_1_uri);
                break;

            case R.id.playlist2_button:
                if(!mplayList2.getText().toString().equals("")) {
                    playlist_2_uri = mplayList2.getText().toString();
                    Toast.makeText(this, "Playlist 2 updated", Toast.LENGTH_LONG).show();
                }
                copyToClipboard(playlist_2_uri);
                break;
        }

    }


    public void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip_uri", text);
        clipboard.setPrimaryClip(clip);
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
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

}

