package com.example.fego;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class UriViewModel extends AndroidViewModel {
    private UriRepository mRepository;
    public static long i = -11;

    private LiveData<List<Uri>> mAllUri;

    public UriViewModel (Application application) {
        super(application);
        mRepository = new UriRepository(application);
        mAllUri = mRepository.getmAllUri();
    }

    LiveData<List<Uri>> getAllUri() { return mAllUri; }

    public Boolean insert (Uri uri) {
        i = mRepository.insert(uri);
        Log.i("TAGG", "i received is : " + i);
        if(i == -1) {
            return false;
        } else return true;
    }

    public Boolean delete (Uri uri) {
        i = mRepository.delete(uri);
        if(i == 0) {
            return false;
        } else return true;
    }

}
