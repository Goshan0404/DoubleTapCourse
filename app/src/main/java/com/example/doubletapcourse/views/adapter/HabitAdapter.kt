package com.example.doubletapcourse.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Habit

class HabitAdapter(private val habits: List<Habit>, private val itemClick: (habit: Habit, position: Int) -> Unit) :
    RecyclerView.Adapter<HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false),
            itemClick
        )
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}