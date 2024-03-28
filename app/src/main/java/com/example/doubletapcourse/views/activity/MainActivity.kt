package com.example.doubletapcourse.views.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.example.doubletapcourse.App
import com.example.doubletapcourse.R
import com.example.doubletapcourse.data.model.Habit
import com.example.doubletapcourse.databinding.ActivityMainBinding
import com.example.doubletapcourse.views.fragments.AboutAppFragment
import com.example.doubletapcourse.views.fragments.AddHabitFragment
import com.example.doubletapcourse.views.fragments.PagerOfHabitListsFragment
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        setAppBar()
        setNavigationListener()
    }


    private fun setNavigationListener() {
        binding.navigationDrawer.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.menu_item_habit_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, PagerOfHabitListsFragment.newInstance())
                        .commit()
                }

                R.id.menu_item_about_app -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.fragment_container, AboutAppFragment.newInstance()
                    ).commit()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)


            return@setNavigationItemSelectedListener true
        }
    }

    private fun setAppBar() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolBar, R.string.open_nav, R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


}

