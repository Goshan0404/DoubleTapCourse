package com.example.doubletapcourse

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitViewHolder(itemView: View, private val itemClick: (habit: Habit, positioni: Int) -> Unit): RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.name_textView)
    private val description: TextView = itemView.findViewById(R.id.description_textView)
    private val type: TextView = itemView.findViewById(R.id.type_textView)
    private val priority: TextView = itemView.findViewById(R.id.priority_textView)
    private val times: TextView = itemView.findViewById(R.id.times_textView)


    fun bind(habit: Habit) {

        name.text = habit.name
        description.text = habit.description
        type.text = habit.type
        priority.text = habit.priority.toString()
        times.text = "${habit.count} in ${habit.interval}"

        itemView.setOnClickListener {
            itemClick(habit, adapterPosition)
        }
    }
}