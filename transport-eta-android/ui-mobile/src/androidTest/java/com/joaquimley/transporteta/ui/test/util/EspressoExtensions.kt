package com.joaquimley.transporteta.ui.test.util

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.not
import io.reactivex.internal.util.NotificationLite.getError



fun Int.click() = onView(withId(this)).perform(ViewActions.click())
fun Int.write(text: String) = onView(withId(this)).perform(typeText(text))
fun Int.textEquals(text: String) = onView(withId(this)).check(matches(withText(text)))

infix fun <T : Activity> ActivityTestRule<T>.containsToast(message: String) =
        onView(withText(message))
                .inRoot(withDecorView(not(activity.window.decorView)))
                .check(matches(isDisplayed()))


//private fun withError(expected: String): Matcher {
//    return object : TypeSafeMatcher() {
//        protected fun matchesSafely(item: View): Boolean {
//            return if (item is EditText) {
//                (item as EditText).getError().toString().equals(expected)
//            } else false
//        }
//
//        fun describeTo(description: Description) {
//            description.appendText("Not found error message [$expected]")
//        }
//    }
//}