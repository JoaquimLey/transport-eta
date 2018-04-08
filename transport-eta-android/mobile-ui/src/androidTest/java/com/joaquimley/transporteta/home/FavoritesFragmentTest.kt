package com.joaquimley.transporteta.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    @get:Rule val activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun fabIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun whenFabIsClickedCreateFavoriteScreenIsShown() {
        onView(withId(R.id.fab)).perform(click())
        onView(withText("Create favorite")).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_code_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(isDisplayed()))
        onView(withText("Create")).check(matches(isDisplayed()))
        onView(withText("Discard")).check(matches(isDisplayed()))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksDiscardDialogIsDismissed() {
        onView(withId(R.id.fab)).perform(click())
        onView(withText("Discard")).perform(click())
        onView(withText("Create favorite")).check(matches(not(isDisplayed())))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksCreateWithNoCodeErrorMessageIsShown() {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        onView(withText("Create")).perform(click())

        onView(withText("Please input bus stop code")).check(matches(isDisplayed()))
    }


}