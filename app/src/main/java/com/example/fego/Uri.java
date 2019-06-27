package com.example.fego;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "uri_table")
public class Uri {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uri")
    private String mUri;

    public Uri(@NonNull String uri) {this.mUri = uri;}

    public String getUri() {
        return this.mUri;
    }
}
