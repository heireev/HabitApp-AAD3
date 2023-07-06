package com.dicoding.habitapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import com.dicoding.habitapp.R

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
class HabitActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HabitListActivity::class.java)

    @Test
    fun testAddHabitButtonOpensAddHabitActivity() {
        onView(withId(R.id.fab)).perform(ViewActions.click())
        onView(withId(R.id.add_habit_activity_layout)).check(matches(ViewMatchers.isDisplayed()))    }
}