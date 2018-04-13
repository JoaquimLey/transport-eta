package com.joaquimley.transporteta.ui.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import com.joaquimley.transporteta.ui.testing.factory.FavoriteViewTestFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    @get:Rule val activityRule = ActivityTestRule(TestFragmentActivity::class.java, true, true)
    private lateinit var favoritesFragment: FavoritesFragment

    @Before
    fun setup() {
        favoritesFragment = FavoritesFragment.newInstance()
        activityRule.activity.addFragment(favoritesFragment)
    }

    @Test
    fun fabIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun whenFabIsClickedCreateFavoriteScreenIsShown() {
        // When
        onView(withId(R.id.fab)).perform(click())
        // Check dialog is showing
        onView(withText("Create favorite")).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_code_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(isDisplayed()))
        onView(withText("Create")).check(matches(isDisplayed()))
        onView(withText("Discard")).check(matches(isDisplayed()))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksDiscardDialogIsDismissed() {
        // Show dialog
        onView(withId(R.id.fab)).perform(click())
        // Click discard button
        onView(withText("Discard")).perform(click())
        // Check is dismissed
        onView(withText("Create favorite")).check(doesNotExist())
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksCreateWithNoCodeErrorMessageIsShown() {
        // Show dialog
        onView(withId(R.id.fab)).perform(click())
        // Be sure the code field is empty
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        // Click create
        onView(withText("Create")).perform(click())
        // Dialog is not dismissed
        onView(withText("Create favorite")).check(matches(isDisplayed()))
        // Error message is shown
        onView(withText("Please input bus stop code")).check(matches(isDisplayed()))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserStarsTypingCodeErrorMessageShouldHide() {
        // Show error message
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        onView(withText("Create")).perform(click())
        // Start typing
        onView(withId(R.id.favorite_code_edit_text)).perform(typeText("1337"))
        // Check error message is hidden
        onView(withText("Please input bus stop code")).check(doesNotExist())
    }


    @Test
    fun whenDataComesInItIsCorrectlyDisplayedOnTheList() {
        FavoriteViewTestFactory.generateFavoriteViewList()
    }
}