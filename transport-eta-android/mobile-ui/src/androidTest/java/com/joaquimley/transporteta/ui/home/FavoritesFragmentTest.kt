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

        `when`(TestFavoriteFragmentModule.favoritesViewModelsFactory.create(FavoritesViewModel::class.java)).thenReturn(viewModel)
        `when`(viewModel.getFavourites()).thenReturn(results)

        favoritesFragment = FavoritesFragment.newInstance()
        activityRule.activity.addFragment(favoritesFragment)
    }

    @Test
    fun createFavoriteViewIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore("Test ignored: Can't currently mock ViewModel, therefore no data comes in")
    fun whenThereAreItemsFavoritesListViewStateIsShown() {
        // When there are items
        // TODO -> Push mock data to UI
        // Then
        onView(withId(R.id.error_view)).check(doesNotExist())
        onView(withId(R.id.empty_view)).check(doesNotExist())
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore("Test ignored: Can't currently mock ViewModel, therefore no empty state is pushed")
    fun whenNoItemAreInTheListEmptyViewStateIsShown() {
        // When
        // TODO Push EMPTY state to UI
        // Then
        onView(withId(R.id.error_view)).check(doesNotExist())
        onView(withId(R.id.recycler_view)).check(doesNotExist())
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore("Test ignored: Can't currently mock ViewModel, therefore no error state is pushed")
    fun whenErrorOccursWithEmptyDataErrorViewStateIsShown() {
        // When
        // ViewModel mock missing: TODO Push EMPTY state to UI
        // ViewModel mock missing: TODO Use argument capture to get the correct error message
        // Then
        onView(withId(R.id.empty_view)).check(doesNotExist())
        onView(withId(R.id.recycler_view)).check(doesNotExist())
        // TODO onView(withText(<__ Use argument capture to get the error message and -> __>)).check(matches(isDisplayed()))
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
    }

    @Test
    @Ignore("Test ignored: Can't currently mock ViewModel, therefore no error state is pushed")
    fun whenErrorOccursWithDataDisplayedErrorMessageIsShown() {
        // When
        // TODO Push SOME DATA to the UI
        // ViewModel mock missing: TODO Use argument capture to get the correct error message

        // Then
        onView(withId(R.id.recycler_view)).check(doesNotExist())
        onView(withId(R.id.empty_view)).check(doesNotExist())
        onView(withId(R.id.error_view)).check(doesNotExist())

        // TODO Check we have a snackbar with error message
        // TODO onView(withText(<__ Use argument capture to get the error message and -> __>)).check(matches(isDisplayed()))
    }

    @Test
    fun whenFabIsClickedCreateFavoriteScreenIsShown() {
        // When
        onView(withId(R.id.fab)).perform(click())
        // Check dialog is showing
        onView(withText(R.string.create_favorite_title)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_code_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.favorite_title_edit_text)).check(matches(isDisplayed()))

        // TODO Hint is shown but espresso can't see it, might be related to focus
//        onView(withText(R.string.create_favorite_code_hint)).check(matches(isDisplayed()))
//        onView(withText(R.string.create_favorite_title_hint)).check(matches(isDisplayed()))

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
//    @Ignore("Test ignored: Not yet implemented, can't currently mock ViewModel")
    fun whenDataComesInItIsCorrectlyDisplayedOnTheList() {
        val resultsList = TestFactoryFavoriteView.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        Thread.sleep(2000)

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