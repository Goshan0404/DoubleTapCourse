package com.example.doubletapcourse.presentation.activity

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.doubletapcourse.R
import com.example.doubletapcourse.databinding.ActivityMainBinding
import com.example.doubletapcourse.presentation.fragments.AboutAppFragment
import com.example.doubletapcourse.presentation.fragments.PagerOfHabitListsFragment


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

        val image = binding.navigationDrawer.getHeaderView(0).findViewById<ImageView>(R.id.header_image)

        Glide.with(this)
            .load("https://avatars.mds.yandex.net/get-mpic/3934197/img_id3959628304575582496.jpeg/orig")
            .placeholder(R.drawable.cat)
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .into(image)
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

