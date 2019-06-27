package com.example.fego;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UriRepository {
    private UriDao mUriDao;
    private LiveData<List<Uri>> mAllUri;

    UriRepository(Application application) {
        UriRoomDatabase db = UriRoomDatabase.getDatabase(application);
        mUriDao = db.uriDao();
        mAllUri = mUriDao.getAllUri();
    }

    LiveData<List<Uri>>  getmAllUri() {
        return mAllUri;
    }


    public void insert (Uri uri) {
        Log.i("TAGG", " in insert:UriRepository");
        new insertAsyncTask(mUriDao, 0).execute(uri);
    }

    public void search (Uri uri) {
        Log.i("TAGG", " in search:UriRepository");
        new insertAsyncTask(mUriDao, 1).execute(uri);
    }


    private static class insertAsyncTask  extends AsyncTask<Uri, Void, Void> {

        private UriDao mAsyncTaskDao;
        int x ;

        insertAsyncTask(UriDao dao, int i) {
            mAsyncTaskDao = dao;
            x = i;
        }

        @Override
        protected Void doInBackground(final Uri... params) {
            if(x == 0) {
                // Insert URI
                mAsyncTaskDao.insert(params[0]);
            }
            else if( x == 1) {
                // Search for URI
                Log.i("TAGG", " Going to UriDao search ");
                mAsyncTaskDao.searchUri(params[0].toString());
                Log.i("TAGG", " came back! ");
                // Logic for searching the database
            }

            Log.i("TAGG", "i is : " + x);
            return null;
        }
    }
}
