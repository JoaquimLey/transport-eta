package com.joaquimley.transporteta.ui.test.util

import android.app.Activity
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not

fun Int.click() = onView(withId(this)).perform(ViewActions.click())
fun Int.write(text: String) = onView(withId(this)).perform(typeText(text))
fun Int.textEquals(text: String) = onView(withId(this)).check(matches(withText(text)))

infix fun <T : Activity> ActivityTestRule<T>.containsToast(message: String) =
        onView(withText(message))
                .inRoot(withDecorView(not(activity.window.decorView)))
                .check(matches(isDisplayed()))