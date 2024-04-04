package com.example.doubletapcourse.presentation.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapcourse.R
import com.example.doubletapcourse.domain.model.Habit

class HabitViewHolder(
    itemView: View,
    private val itemClick: (habit: Habit) -> Unit,
    private val doneClick: (habit: Habit) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.name_textView)
    private val description: TextView = itemView.findViewById(R.id.description_textView)
    private val type: TextView = itemView.findViewById(R.id.type_textView)
    private val priority: TextView = itemView.findViewById(R.id.priority_textView)
    private val times: TextView = itemView.findViewById(R.id.times_textView)
    private val doneButton: TextView = itemView.findViewById(R.id.done_button)


    fun bind(habit: Habit) {
        name.text = habit.name
        description.text = habit.description
        type.text = habit.type.toString()
        priority.text = habit.priority.toString()
        times.text = itemView.context.getString(
            R.string.habit_times,
            habit.intervalCount.toString(),
            habit.interval
        )

        doneButton.setOnClickListener {
            doneClick(habit)
        }

        itemView.setOnClickListener {
            itemClick(habit)
        }
    }
}