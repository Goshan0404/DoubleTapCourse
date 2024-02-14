package com.example.doubletapcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SquareNumberActivity : AppCompatActivity() {
    private lateinit var numText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square_number)

        numText = findViewById(R.id.sueqre_number_tv_id)

        if (intent.extras != null && intent.extras!!.containsKey(Constants.NUMBER))
        numText.text = Math.pow(
            intent.getIntExtra(Constants.NUMBER, 0).toDouble(), 2.0)
            .toInt()
            .toString()

    }
}