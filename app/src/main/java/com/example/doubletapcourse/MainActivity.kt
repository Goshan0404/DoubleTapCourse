package com.example.doubletapcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var numberTextView: TextView
    private lateinit var toNextActivity: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberTextView = findViewById(R.id.number_tv_id)
        toNextActivity = findViewById(R.id.toNext_button_id)

        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.NUMBER)) {
            numberTextView.text = (savedInstanceState.getInt(Constants.NUMBER)+1).toString()
        }

        toNextActivity.setOnClickListener {
            val intent = Intent(this, SquareNumberActivity::class.java).apply {
                putExtra(Constants.NUMBER, numberTextView.text.toString().toInt())
            }

            startActivity(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.NUMBER, numberTextView.text.toString().toInt())
    }


    override fun onStart() {
        super.onStart()
        Log.d("start", "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("stop", "onStart: ")

    }

    override fun onPause() {
        super.onPause()
        Log.d("pause", "onStart: ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy", "onStart: ")

    }
}

