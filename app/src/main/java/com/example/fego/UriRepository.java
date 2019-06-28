package com.example.fego;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import static com.example.fego.UriViewModel.i;

public class UriRepository {
    private UriDao mUriDao;
    private LiveData<List<Uri>> mAllUri;
    public static long x = -11;
    public static int y = -19;

    UriRepository(Application application) {
        UriRoomDatabase db = UriRoomDatabase.getDatabase(application);
        mUriDao = db.uriDao();
        mAllUri = mUriDao.getAllUri();
    }

    LiveData<List<Uri>>  getmAllUri() {
        return mAllUri;
    }


    public long insert (Uri uri) {
        new insertAsyncTask(mUriDao).execute(uri);
        try {
            Thread.sleep(100);
        } catch(InterruptedException exc) {
            Log.i("TAGG", "Exception");
        }
        Log.i("TAGG", "o returned is : " + x);
        return x;
    }

    public int delete(Uri uri) {
        new deleteAsyncTask(mUriDao).execute(uri);
        try {
            Thread.sleep(100);
        } catch(InterruptedException exc) {
            Log.i("TAGG", "Exception");
        }
        Log.i("TAGG", "y is : " + y);
        return y;
    }


    private static class insertAsyncTask  extends AsyncTask<Uri, Void, Long> {

        private UriDao mAsyncTaskDao;

        insertAsyncTask(UriDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Uri... params) {
            // Insert URI
            x = mAsyncTaskDao.insert(params[0]);
            Log.i("TAGG", "x after background work is : " + x);
            return x;
        }
    }



    private static class deleteAsyncTask extends AsyncTask<Uri, Void, Integer> {
        private UriDao mAsyncTaskDao;

        deleteAsyncTask(UriDao dao) { mAsyncTaskDao =  dao; }

        @Override
        protected Integer doInBackground(final Uri... params) {
            // Delete URI
            y = mAsyncTaskDao.delete(params[0]);
            Log.i("TAGG", "y after background work is : " + y);
            return y;
        }

    }
}
