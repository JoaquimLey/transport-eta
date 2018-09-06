package com.joaquimley.transporteta.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
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

    @Rule @JvmField val activityRule = ActivityTestRule(TestFragmentActivity::class.java, true, true)

    @Before
    fun setup() {

    }

    @Test
    fun whenActivityIsStartedFavoritesFragmentIsShown() {
        onView(withText("Favorites")).check(matches(isDisplayed()))
    }

    // Check this article for permissions test
    // https://blog.egorand.me/testing-runtime-permissions-lessons-learned/

    // https://medium.com/exploring-android/handling-android-runtime-permissions-in-ui-tests-981f9dc11a4e

    // https://www.kotlindevelopment.com/runtime-permissions-espresso-done-right/

    // https://developer.android.com/reference/android/support/test/rule/GrantPermissionRule.html

    // https://stackoverflow.com/questions/25998659/espresso-how-can-i-check-if-an-activity-is-launched-after-performing-a-certain

    // https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsBasicSample

    // https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsAdvancedSample
}