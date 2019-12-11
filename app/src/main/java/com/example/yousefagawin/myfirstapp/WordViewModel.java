package com.example.yousefagawin.myfirstapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    //private member variable to hold a reference to the repository
    private WordRepository mRepository;

    //variable to cache the list of words
    private LiveData<List<Word>> mAllWords;

    //constructor that gets a reference to the repository
    // and gets the list of words from the repository
    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //a "getter" method for all the words.
    // This completely hides the implementation from the UI
    LiveData<List<Word>> getAllWords() { return mAllWords; }

    //Created a wrapper insert() method that calls the Repository's insert() method.
    // In this way, the implementation of insert() is completely hidden from the UI.
    public void insert(Word word) { mRepository.insert(word); }
}

//WARNING: Never pass context into ViewModel instances.
// Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
//
//For example, an Activity can be destroyed and created many times during the lifecycle
// of a ViewModel as the device is rotated.
// If you store a reference to the Activity in the ViewModel,
// you end up with references that point to the destroyed Activity.
// This is a memory leak.
//
//If you need the application context, use AndroidViewModel, as shown in this codelab.

//IMPORTANT: ViewModel is not a replacement for the onSaveInstanceState() method,
// because the ViewModel does not survive a process shutdown.