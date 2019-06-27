package com.example.fego;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Uri.class}, version  = 1)
public abstract class UriRoomDatabase extends RoomDatabase {
    public abstract UriDao uriDao();

    private static volatile UriRoomDatabase INSTANCE;

    static UriRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (UriRoomDatabase.class) {
                if(INSTANCE == null) {
                    // Create database here
                    INSTANCE  = Room.databaseBuilder(context.getApplicationContext(), UriRoomDatabase.class, "uri_database").addCallback(sRoomDatabaseCalBack).build();

                }
            }

        }
        return INSTANCE;
    }

    private static UriRoomDatabase.Callback sRoomDatabaseCalBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UriDao mDao;

        PopulateDbAsync(UriRoomDatabase db) {
            mDao = db.uriDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Uri uri = new Uri("Hello");
            mDao.insert(uri);
            uri = new Uri("World");
            mDao.insert(uri);
            return null;
        }
    }





}
