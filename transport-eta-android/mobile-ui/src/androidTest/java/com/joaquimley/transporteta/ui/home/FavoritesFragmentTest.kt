package com.joaquimley.transporteta.ui.home

import android.arch.lifecycle.MutableLiveData
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
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.Resource
import com.joaquimley.transporteta.ui.test.util.RecyclerViewMatcher
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import com.joaquimley.transporteta.ui.testing.factory.TestFactoryFavoriteView
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@MediumTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    @Rule @JvmField val activityRule = ActivityTestRule(TestFragmentActivity::class.java)

    @Mock lateinit var viewModel: FavoritesViewModel
//     lateinit var viewModel: FavoritesViewModel

    private lateinit var favoritesFragment: FavoritesFragment
    private val results = MutableLiveData<Resource<List<FavoriteView>>>()

    @Before
    fun setup() {
        // Mock ViewModel
        MockitoAnnotations.initMocks(this)
//        viewModel = mock<FavoritesFragment>()
        `when`(viewModel.getFavourites()).thenReturn(results)

        // Init Activity&Fragment
        favoritesFragment = FavoritesFragment.newInstance()
        activityRule.activity.addFragment(favoritesFragment)
    }

    @Test
    fun createFavoriteViewIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun favoritesListViewIsShown() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
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
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(hasDescendant(withText(resultsList[0].code.toString()))))
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }

    // https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
    // https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e

}