package com.example.appproject2.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="documents")
public class Document {

    public Document(int id, String documentName, String uri) {
        this.id = id;
        this.documentName = documentName;
        this.uri = uri;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int id;

    @ColumnInfo(name = "documentName")
    public String documentName;

    @ColumnInfo(name = "uri")
    public String uri;
}
