package com.joaquimley.transporteta.ui.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@MediumTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setup() {
        activityRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun whenActivityIsStartedFavoritesFragmentIsShown() {
        onView(withText("Favorites")).check(matches(isDisplayed()))
    }

    // Check this article for permissions test
    // https://blog.egorand.me/testing-runtime-permissions-lessons-learned/
}