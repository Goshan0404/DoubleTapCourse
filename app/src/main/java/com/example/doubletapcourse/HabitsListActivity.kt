package com.example.doubletapcourse

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HabitsListActivity : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_list)

        addButton = findViewById(R.id.add_habit)
        recyclerView = findViewById(R.id.habits_list)

        var habitItems = mutableListOf<Habit>()


        val activityLauncherForAdd =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val habit =
                        result.data?.getParcelableExtra(ExtraConstants.HABIT, Habit::class.java)

                    habit?.let {
                        habitItems.add(it)
                        adapter.notifyItemInserted(habitItems.size - 1)
                    }

                }
            }

        var itemSelectedPosition = 0
        val intentForEditResult = Intent(this, AddHabitActivity::class.java)
        val activityLauncherForEdit =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val habit =
                        result.data?.getParcelableExtra(ExtraConstants.HABIT, Habit::class.java)

                    habit?.let {
                        habitItems[itemSelectedPosition] = habit
                        adapter.notifyItemChanged(itemSelectedPosition)
                    }

                }
            }

        adapter = HabitAdapter(habitItems) { habit: Habit, position: Int ->

            intentForEditResult.putExtra(ExtraConstants.HABIT, habit)
            itemSelectedPosition = position

            activityLauncherForEdit.launch(intentForEditResult)
        }

        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL,

        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        addButton.setOnClickListener {
            activityLauncherForAdd.launch(Intent(this, AddHabitActivity::class.java))
        }
    }
}

