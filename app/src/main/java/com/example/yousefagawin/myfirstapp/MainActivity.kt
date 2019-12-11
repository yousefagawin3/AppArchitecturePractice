package com.example.yousefagawin.myfirstapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //Use ViewModelProviders to associate your ViewModel with your UI controller.
    // When your app first starts,
    // the ViewModelProviders will create the ViewModel.
    // When the activity is destroyed,
    // for example through a configuration change,
    // the ViewModel persists.
    // When the activity is re-created,
    // the ViewModelProviders return the existing ViewModel.
    private var mWordViewModel: WordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java!!)

        //Observer for the LiveData returned by getAllWords().
        mWordViewModel!!.allWords.observe(this, Observer { words ->
            //The onChanged() method fires when the observed data changes and the activity is in the foreground.
            // Update the cached copy of the words in the adapter.
            adapter.setWords(words)
        })
    }


    //If the activity returns with RESULT_OK,
    // insert the returned word into the database by calling the insert() method of the WordViewModel.
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val word = Word(data!!.getStringExtra(NewWordActivity.EXTRA_REPLY))
            mWordViewModel!!.insert(word)
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
    }

}
