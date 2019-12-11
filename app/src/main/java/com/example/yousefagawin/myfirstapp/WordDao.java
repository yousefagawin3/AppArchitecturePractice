package com.example.yousefagawin.myfirstapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


//Annotated the class with @Dao to identify it as a DAO class for Room.
@Dao

public interface WordDao {

    //Annotate the method with @Insert.
    // You don't have to provide any SQL!
    // (There are also @Delete and @Update annotations for deleting and updating a row,
    // but you are not using them in this app.)
    @Insert
    void insert(Word word);

    //There is no convenience annotation for deleting multiple entities,
    // so annotate the method with the generic @Query.
    //Provided the SQL query as a string parameter to @Query.
    // Used @Query for read and complicated queries and provided SQL.
    @Query("DELETE FROM word_table")
    //this method is used to delete all the words
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")

    //method to get all the words
    LiveData<List<Word>> getAllWords();
}

//TIP: For this app, ordering the words is not strictly necessary.
// However, by default, order is not guaranteed,
// and ordering makes testing straightforward.

//TIP: When inserting data, you can provide a conflict strategy.
//
//In this codelab, you do not need a conflict strategy,
// because the word is your primary key,
// and the default SQL behavior is ABORT,
// so that you cannot insert two items with the same primary key into the database.
//
//If the table has more than one column, you can use
// @Insert(onConflict = OnConflictStrategy.REPLACE)
//to replace a row.