package com.example.pokepedia

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonUiTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pokepedia", appContext.packageName)
    }

    @Test
    fun isRecyclerVisible() {
        onView(withId(R.id.pokemonListRecycler))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onItemClick() {
        onView(withId(R.id.pokemonListRecycler))
            .check(matches(isDisplayed()))

        onView(withId(R.id.pokemonListRecycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.constraintLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.nameTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.heightTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.weightTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.expTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.progressBar))
            .check(matches(not(isDisplayed())))
    }
}