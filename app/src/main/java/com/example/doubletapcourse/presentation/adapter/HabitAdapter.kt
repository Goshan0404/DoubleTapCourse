package com.example.doubletapcourse.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapcourse.R
import com.example.doubletapcourse.presentation.model.Habit

class HabitAdapter(
    private val itemClick: (habit: Habit) -> Unit,
    private val doneClick: (habit: Habit) -> Unit
) :
    RecyclerView.Adapter<HabitViewHolder>() {

    private var habits = emptyList<Habit>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false),
            itemClick, doneClick
        )
    }

    fun setData(newHabit: List<Habit>) {
        val diffUtilCallback = HabitsDiffUtilCallback(habits, newHabit)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        habits = newHabit
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}

class HabitsDiffUtilCallback(private val oldList: List<Habit>, private val newList: List<Habit>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}