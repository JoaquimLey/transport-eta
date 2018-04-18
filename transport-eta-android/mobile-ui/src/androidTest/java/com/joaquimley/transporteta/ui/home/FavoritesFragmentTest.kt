package com.joaquimley.transporteta.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
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
import com.joaquimley.transporteta.ui.di.module.TestFavoriteFragmentModule
import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import com.joaquimley.transporteta.ui.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.ui.model.FavoriteView
import com.joaquimley.transporteta.ui.model.data.Resource
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import com.joaquimley.transporteta.ui.testing.factory.TestFactoryFavoriteView
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@MediumTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    @Rule @JvmField val activityRule = ActivityTestRule(TestFragmentActivity::class.java, true, true)
    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val results = MutableLiveData<Resource<List<FavoriteView>>>()
    private val viewModel = mock(FavoritesViewModel::class.java)

    private lateinit var favoritesFragment: FavoritesFragment

    @Before
    fun setup() {
        // Init mock ViewModel
        `when`(TestFavoriteFragmentModule.favoritesViewModelsFactory.create(FavoritesViewModel::class.java)).thenReturn(viewModel)
        `when`(viewModel.getFavourites()).thenReturn(results)
        // Instantiate fragment and add to the TestFragmentActivity
        favoritesFragment = FavoritesFragment.newInstance()
        activityRule.activity.addFragment(favoritesFragment)
    }

    @Test
    fun createFavoriteViewIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun whenThereIsDataSuccessViewStateIsShown() {
        // When there are items
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // Then
        onView(withId(R.id.message_view)).check(doesNotExist())
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenThereIsNoDataEmptyViewStateIsShown() {
        // When
        results.postValue(Resource.empty())
        // Then
        onView(withId(R.id.recycler_view)).check(doesNotExist())
        onView(withId(R.id.message_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenErrorOccursNoDataErrorViewStateIsShown() {
        // When empty
        results.postValue(Resource.empty())
        // Error occurs
        val errorMessage = "Test for error message"
        results.postValue(Resource.error(errorMessage))
        // Then
        onView(withId(R.id.message_view)).check(doesNotExist())
        onView(withId(R.id.recycler_view)).check(doesNotExist())
    }

    @Test
    fun whenErrorOccursWithDataDisplayedErrorMessageIsShown() {
        // When there is data
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        // Error occurs
        val errorMessage = "Test for error message"
        results.postValue(Resource.error(errorMessage))
        // Then
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.message_view)).check(doesNotExist())
        // Only snackbar is shown with retry button
        onView(withText(errorMessage)).check(matches(isDisplayed()))
        onView(withText(R.string.action_retry)).check(matches(isDisplayed()))
    }

    @Test
    fun whenFabIsClickedCreateFavoriteScreenIsShown() {
        // When
        onView(withId(R.id.fab)).perform(click())
        // Check dialog is showing
        onView(withText(R.string.create_favorite_title)).check(matches(isDisplayed()))
        // Check
        onView(withId(R.id.favorite_code_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_code_edit_text)).check(matches(withHint(R.string.create_favorite_code_hint)))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(withHint(R.string.create_favorite_title_hint)))

        onView(withText(R.string.action_create)).check(matches(isDisplayed()))
        onView(withText(R.string.action_discard)).check(matches(isDisplayed()))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksDiscardDialogIsDismissed() {
        // Show dialog
        onView(withId(R.id.fab)).perform(click())
        // Click discard button
        onView(withText(R.string.action_discard)).perform(click())
        // Check is dismissed
        onView(withText(R.string.create_favorite_title)).check(doesNotExist())
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksCreateWithNoCodeErrorMessageIsShown() {
        // Show dialog
        onView(withId(R.id.fab)).perform(click())
        // Be sure the code field is empty
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        // Click create
        onView(withText(R.string.action_create)).perform(click())
        // Dialog is not dismissed
        onView(withText(R.string.create_favorite_title)).check(matches(isDisplayed()))
        // Error message is shown
        onView(withText(R.string.error_create_favorite_code_required)).check(matches(isDisplayed()))
    }

    @Test
    fun inCreateFavoriteDialogWhenUserStarsTypingCodeErrorMessageShouldHide() {
        // Show error message
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        onView(withText(R.string.action_create)).perform(click())
        // Start typing
        onView(withId(R.id.favorite_code_edit_text)).perform(typeText("1337"))
        // Check error message is hidden
        onView(withText(R.string.error_create_favorite_code_required)).check(doesNotExist())
    }


    @Test
    @Ignore("Test ignored: Not yet implemented, can't currently mock ViewModel")
    fun whenDataComesInItIsCorrectlyDisplayedOnTheList() {
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
//        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(0))
//                .check(matches(hasDescendant(withText(resultsList[0].latestEta))))
//        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(0)).check(matches(hasDescendant(withText(resultsList[0].code.toString()))))
//        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
//        onView(withText(resultsList[0].code.toString())).check(matches(isDisplayed()))
//        viewModel.getFavourites()
    }

}

// https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
// https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e