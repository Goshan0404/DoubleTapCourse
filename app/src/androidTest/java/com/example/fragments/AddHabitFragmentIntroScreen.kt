package com.example.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.doubletapcourse.R
import com.example.doubletapcourse.presentation.activity.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddHabitFragmentIntroScreen {

    @JvmField
    @Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testLoginSuccess() {

        onView(withId(R.id.add_habit)).perform(click())

        onView(withId(R.id.habit_name_textView)).perform(typeText("user123"))
        onView(withId(R.id.habit_description_textView)).perform(typeText("user123"))
        onView(withId(R.id.count_editText)).perform(typeText("2"))

        onView(withId(R.id.interval_spinner))
            .perform(click());
        onView(withText(R.string.week))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .perform(click())

        onView(withId(R.id.priority_spinner))
            .perform(click());
        onView(withText(R.string.high))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .perform(click())

        onView(withId(R.id.useful_radioButton)).perform(click())



        onView(withId(R.id.priority_spinner))
            .check(matches(withText(R.string.high)));
        onView(withId(R.id.interval_spinner))
            .check(matches(withText(R.string.week)));
        onView(withId(R.id.count_editText))
            .check(matches(withText("02")));
        onView(withId(R.id.habit_description_textView))
            .check(matches(withText("user123")));
        onView(withId(R.id.habit_name_textView))
            .check(matches(withText("user123")));


    }
}