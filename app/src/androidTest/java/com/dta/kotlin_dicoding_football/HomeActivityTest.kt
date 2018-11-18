package com.dta.kotlin_dicoding_football

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.dta.kotlin_dicoding_football.R.id.add_to_favorite
import com.dta.kotlin_dicoding_football.R.id.snackbar_text
import com.dta.kotlin_dicoding_football.View.MatchOnline.MatchOnlineActivity
import com.dta.kotlin_dicoding_football.View.MatchOnline.MatchOnlineActivity.Companion.bottomNavigation
import com.dta.kotlin_dicoding_football.View.MatchOnline.MatchOnlineActivity.Companion.rvListMatch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchOnlineActivity::class.java)
    @Test
    fun testAppBehaviour(){
        // Click menu
        loading()
        Espresso.onView(ViewMatchers.withId(rvListMatch))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        loading()

        Espresso.onView(ViewMatchers.withId(rvListMatch))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(ViewMatchers.withId(rvListMatch))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, ViewActions.click()))

        loading()
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        loading()

        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(snackbar_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Added to favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        loading()

        Espresso.onView(ViewMatchers.withId(bottomNavigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
    private fun loading(){
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}