package com.example.doubletapcourse.views.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapcourse.data.HabitStore
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.data.model.Interval
import com.example.doubletapcourse.data.model.Priority
import com.example.doubletapcourse.data.model.Type
import com.example.doubletapcourse.views.fragments.AddHabitFragment
import kotlinx.coroutines.launch
import java.util.UUID

class AddHabitViewModel(application: Application) : ViewModel() {
    private val habitStore = HabitStore(application)
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var type: String? = null
    var priority: String? = null
    var count: String? = null
    var interval: String? = null

    fun saveHabit(success: () -> Unit, unSuccess: () -> Unit) {
        if (name == null || description == null || type == null || priority == null || count == null || interval == null) {
            unSuccess()
            return
        }

        val habit = getHabit()
        viewModelScope.launch {

            habitStore.save(habit)
        }

        success()
    }

    private fun getHabit(): Habit {


        return Habit(
            id ?: UUID.randomUUID().toString(),
            name!!,
            description!!,
            Type.valueOf(type!!),
            Priority.valueOf(priority!!),
            count!!.toInt(),
            Interval.valueOf(interval!!)
        )

    }

}