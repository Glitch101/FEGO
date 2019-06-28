package com.example.fego;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UriDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Uri uri);

    @Query("DELETE FROM uri_table")
    void deleteAll();

    @Delete
    int delete(Uri uri);

    @Query("SELECT * from uri_table ORDER BY uri ASC")
    LiveData<List<Uri>> getAllUri();


}
