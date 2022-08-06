package com.example.tictactoe

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GameTest {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun player1Wins() {
        onView(withId(R.id.button_00)).perform(click())
        onView(withId(R.id.button_11)).perform(click())
        onView(withId(R.id.button_01)).perform(click())
        onView(withId(R.id.button_21)).perform(click())
        onView(withId(R.id.button_02)).perform(click())
        onView(withId(R.id.text_view_player1))
            .check(matches(withText(containsString("Player 1: 1"))))
    }

    @Test
    fun player2Wins() {
        onView(withId(R.id.button_00)).perform(click())
        onView(withId(R.id.button_01)).perform(click())
        onView(withId(R.id.button_02)).perform(click())
        onView(withId(R.id.button_11)).perform(click())
        onView(withId(R.id.button_12)).perform(click())
        onView(withId(R.id.button_21)).perform(click())
        onView(withId(R.id.text_view_player2))
            .check(matches(withText(containsString("Player 2: 1"))))
    }
}