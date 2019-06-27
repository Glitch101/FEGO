package com.example.fego;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UriDao {
    @Insert
    void insert(Uri uri);

    @Query("DELETE FROM uri_table")
    void deleteAll();

    @Query("SELECT * from uri_table ORDER BY uri ASC")
    LiveData<List<Uri>> getAllUri();

    @Query("SELECT * FROM uri_table WHERE uri LIKE :nUri")
    LiveData<List<Uri>> searchUri(String nUri);
    // TODO: After searching URI, return true or false

}
