package com.joaquimley.transporteta.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.ui.di.module.TestFavoriteFragmentModule
import com.joaquimley.transporteta.ui.home.favorite.FavoritesAdapter
import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import com.joaquimley.transporteta.ui.test.util.RecyclerViewMatcher
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import com.joaquimley.transporteta.ui.testing.factory.TestFactoryFavoriteView
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
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

    @Rule @JvmField val activityRule = ActivityTestRule(TestFragmentActivity::class.java, false, true)
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
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.message_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenThereIsNoDataEmptyViewStateIsShown() {
        // When
        results.postValue(Resource.empty())
        // Then
        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.message_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenThereIsNoDataAndErrorOccursErrorViewStateIsShown() {
        // When empty
        results.postValue(Resource.empty())
        // Error occurs
        val errorMessage = "Test for error message"
        results.postValue(Resource.error(errorMessage))
        // Then
        onView(withId(R.id.message_view)).check(matches(isDisplayed()))
        onView(withText(errorMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())))
    }

    /**
     * Due to Android P non-sdk access an alert dialog is shown making this test flaky
     *
     * Issue: android.support.test.espresso.NoMatchingViewException:
     * No views in hierarchy found matching: with id: com.joaquimley.transporteta.debug:id/recycler_view
     *
     * https://developer.android.com/preview/restrictions-non-sdk-interfaces.html
     */
    @Test
    fun whenThereIsDataAndErrorOccursErrorMessageIsShown() {
        // When there is data
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // List is displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        // Error occurs
        val errorMessage = "Error message for Test"
        results.postValue(Resource.error(errorMessage))

        // Then correct views are displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.message_view)).check(matches(not(isDisplayed())))

        // Snackbar is shown with retry button
        onView(withText(errorMessage)).check(matches(isDisplayed()))
        onView(withText(R.string.action_retry)).check(matches(isDisplayed()))
    }

    @Test
    fun whenCreateFabIsClickedCreateFavoriteScreenIsShown() {
        // When
        onView(withId(R.id.fab)).perform(click())
        // Check dialog is showing correctly
        onView(withText(R.string.create_favorite_title)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_code_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(isDisplayed()))

        // TODO Fix hints
//        onView(withText(R.string.create_favorite_code_hint)).check(matches(isDisplayed()))
//        onView(withText(R.string.create_favorite_title_hint)).check(matches(isDisplayed()))
        // Dialog Action buttons
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
        onView(withText(R.string.action_create)).check(doesNotExist())
        onView(withText(R.string.action_discard)).check(doesNotExist())
    }

    @Test
    fun inCreateFavoriteDialogWhenUserClicksCreateWithNoCodeErrorMessageIsShown() {
        // Show error message
        onView(withId(R.id.fab)).perform(click())
        // Be sure the code field is empty
        onView(withId(R.id.favorite_code_edit_text)).perform(clearText())
        // Click create
        onView(withText(R.string.action_create)).perform(click())
        // Dialog is not dismissed
        onView(withText(R.string.create_favorite_title)).check(matches(isDisplayed()))
        // Error message is shown
        val errorMessage = activityRule.activity.getString(R.string.error_create_favorite_code_required)
        onView(withId(R.id.favorite_code_edit_text)).check(matches(hasErrorText(errorMessage))).check(matches(isDisplayed()))
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
        onView(withId(R.id.favorite_code_edit_text)).check(matches(hasErrorText(nullValue(String::class.java))))
    }


    @Test
    fun whenThereIsDataItIsCorrectlyDisplayedOnTheList() {
        // When
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // Then check all items
        for (favoriteView in resultsList.withIndex()) {
            // Scroll to item INDEX
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<FavoritesAdapter.FavoriteViewHolder>(favoriteView.index))
            // Check item is displayed correctly
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].code.toString()))))
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].latestEta))))
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].originalText))))
        }
    }
}


// https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
// https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
// https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e