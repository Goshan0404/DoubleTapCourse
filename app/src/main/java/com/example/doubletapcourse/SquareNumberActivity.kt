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


        numText.text = (intent.getIntExtra(Constants.NUMBER, 0) *
                intent.getIntExtra(Constants.NUMBER, 0)).toString()


    }
}