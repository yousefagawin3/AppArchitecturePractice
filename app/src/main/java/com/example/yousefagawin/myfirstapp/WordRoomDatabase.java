package com.example.yousefagawin.myfirstapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

//Annotated the class to be a Room database,
//declared the entities that belong in the database and set the version number.
// Listing the entities will create tables in the database.
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    //Defined the DAOs that work with the database.
    // Provide an abstract "getter" method for each @Dao.
    public abstract WordDao wordDao();


    //Made the WordRoomDatabase a singleton to prevent having multiple instances of the database
    // opened at the same time.
    private static volatile WordRoomDatabase INSTANCE;
    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {

                    //Added the code to get a database.
                    // This code uses Room's database builder to create a RoomDatabase object
                    //in the application context from the WordRoomDatabase class and names it "word_database".
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //To delete all content and repopulate the database whenever the app is started
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                //Because you cannot do Room database operations on the UI thread,
                // onOpen() creates and executes an AsyncTask to add content to the database.
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    //Here is the code for the AsyncTask that deletes the contents of the database,
    // then populates it with the two words "Hello" and "World".
    // Feel free to add more words!
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Hello");
            mDao.insert(word);
            word = new Word("World");
            mDao.insert(word);
            return null;
        }
    }
}
