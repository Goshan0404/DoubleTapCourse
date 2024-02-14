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

    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")


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
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")

    }
}

