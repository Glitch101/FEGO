package com.example.fego;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Uri.class}, version  = 1)
public abstract class UriRoomDatabase extends RoomDatabase {
    public abstract UriDao uriDao();

    private static volatile UriRoomDatabase INSTANCE;

    static UriRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (UriRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create database here
                    INSTANCE  = Room.databaseBuilder(context.getApplicationContext(), UriRoomDatabase.class, "uri_database").build();

                }
            }

        }
        return INSTANCE;
    }
}
