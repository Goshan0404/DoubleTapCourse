package com.example.doubletapcourse

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class AddHabitActivity : AppCompatActivity() {
    private lateinit var name: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var type: RadioGroup
    private lateinit var priority: AutoCompleteTextView
    private lateinit var saveButton: Button
    private lateinit var count: TextInputEditText
    private lateinit var interval: AutoCompleteTextView


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        name = findViewById(R.id.name_textView_add_activity)
        description = findViewById(R.id.description_textView_add_activity)
        type = findViewById(R.id.type_radioGroup_add_activity)
        priority = findViewById(R.id.priority_spinner_add_activity)
        saveButton = findViewById(R.id.save_button_add_activity)
        count = findViewById(R.id.count_editText_add_activity)
        interval = findViewById(R.id.interval_spinner_add_activity)


        val habit = intent.getParcelableExtra(ExtraConstants.HABIT, Habit::class.java)

        habit?.let {
            name.setText(it.name)
            description.setText(it.description)
            count.setText(it.count.toString())
            // Вопрос: почему не работает
//            interval.setSelection((interval.adapter as ArrayAdapter<String>).getPosition(habit.interval.toString()))
//            priority.setSelection((priority.adapter as ArrayAdapter<String>).getPosition(habit.priority.toString()))

        }

        saveButton.setOnClickListener {


            // Вопрос: как это сделать нормально или это нормально) ?
            if (type.checkedRadioButtonId == -1 ||
                isEmptyFields(
                    name.text.toString(), description.text.toString(),
                    (findViewById<RadioButton>(type.checkedRadioButtonId)).text.toString(),
                    priority.text.toString(), count.text.toString(), interval.text.toString()
                )
            ) {
                Toast.makeText(this, "fields must by not empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent()
            intent.putExtra(
                ExtraConstants.HABIT,
                Habit(
                    name.text.toString(),
                    description.text.toString(),
                    (findViewById<RadioButton>(type.checkedRadioButtonId)).text.toString(),
                    Priority.valueOf(priority.text.toString()),
                    count.text.toString().toInt(),
                    Interval.valueOf(interval.text.toString())
                )
            )
            setResult(RESULT_OK, intent)
            finish()
        }


    }

    fun isEmptyFields(vararg strings: String): Boolean {
        for (string in strings) {
            if (string.isEmpty())
                return true
        }
        return false
    }

}