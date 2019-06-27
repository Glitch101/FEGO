package com.example.fego;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UriViewModel extends AndroidViewModel {
    private UriRepository mRepository;

    private LiveData<List<Uri>> mAllUri;

    public UriViewModel (Application application) {
        super(application);
        mRepository = new UriRepository(application);
        mAllUri = mRepository.getmAllUri();
    }

    LiveData<List<Uri>> getAllUri() { return mAllUri; }

    public void insert (Uri uri) { mRepository.insert(uri); }

    public void search (Uri uri) {
        Log.i("TAGG", " in search:UriViewModel");
        mRepository.search(uri);
     /*   if(mRepository.search(uri) == true) {
            return true;
        } else return false;*/

    }

}
