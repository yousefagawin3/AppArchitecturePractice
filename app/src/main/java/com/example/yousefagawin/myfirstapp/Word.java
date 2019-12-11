package com.example.yousefagawin.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


//Each @Entity class represents an entity in a table.
// Annotate your class declaration to indicate that it's an entity.
// Specify the name of the table if you want it to be different from the name of the class.
@Entity(tableName = "word_table")
public class Word {

    //Every entity needs a primary key. To keep things simple, each word acts as its own primary key.
    @PrimaryKey

    //Denotes that a parameter, field, or method return value can never be null.
    @NonNull

    //Specify the name of the column in the table if you want it to be
    // different from the name of the member variable.
    @ColumnInfo(name = "word")

    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}

    //Every field that's stored in the database needs to be either public or have a "getter" method.
    //This sample provides a getWord() method.
    public String getWord(){return this.mWord;}
}

//TIP: You can autogenerate unique keys by annotating the primary key as follows:
//
//@Entity(tableName = "word_table")
//public class Word {
//
//@PrimaryKey(autoGenerate = true)
//private int id;
//
//@NonNull
//private String word;
////..other fields, getters, setters
//}