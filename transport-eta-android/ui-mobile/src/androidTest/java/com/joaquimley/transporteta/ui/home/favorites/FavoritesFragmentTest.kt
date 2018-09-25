package com.joaquimley.transporteta.ui.home.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.ui.di.module.TestFavoriteFragmentModule
import com.joaquimley.transporteta.ui.home.favorite.FavoritesAdapter
import com.joaquimley.transporteta.ui.home.favorite.FavoritesFragment
import com.joaquimley.transporteta.ui.test.util.RecyclerViewMatcher
import com.joaquimley.transporteta.ui.testing.TestFragmentActivity
import com.joaquimley.transporteta.ui.testing.factory.TestModelsFactory
import com.joaquimley.transporteta.ui.util.extensions.findViewById
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    // Rules
    @Rule @JvmField val activityRule = ActivityTestRule(TestFragmentActivity::class.java)
    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test subject
    private lateinit var favoritesFragment: FavoritesFragment

    // Mocks
    private val viewModel = mock(FavoritesViewModel::class.java)

    // LiveData
    private val requestsAvailable = MutableLiveData<Boolean>()
    private val results = MutableLiveData<Resource<List<TransportView>>>()

    @Before
    fun setup() {
        //        `when`(TestFavoriteFragmentModule.favoritesViewModelFactory.create(FavoritesViewModel::class.java)).thenReturn(viewModel)

        // Init ViewModel mock
//        viewModel = mock(FavoritesViewModel::class.java)
        `when`(viewModel.getFavorites()).thenReturn(results)
        `when`(viewModel.isAcceptingRequests()).thenReturn(requestsAvailable)

        favoritesFragment = FavoritesFragment.newInstance()
//        favoritesFragment.viewModelProvider = ViewModelUtil.createFor(viewModel) as FavoritesViewModelFactory

        `when`(TestFavoriteFragmentModule.favoritesViewModelFactory.create()).thenReturn(viewModel)
        favoritesFragment = FavoritesFragment.newInstance()
//        `when`(favoritesFragment.viewModelProvider.invoke(favoritesFragment)).thenReturn(viewModel)

        activityRule.activity.addFragment(favoritesFragment)
    }

    @Test
    fun createFavoriteViewIsShown() {
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun whenThereIsDataSuccessViewStateIsShown() {
        // When there are items
        val resultsList = TestModelsFactory.generateFavoriteViewList()
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

    @Test
    fun whenThereIsDataAndErrorOccursErrorMessageIsShown() {
        // When there is data
        val resultsList = TestModelsFactory.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // List is displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        // Error occurs
        val errorMessage = "Error message for Test"
        results.postValue(Resource.error(errorMessage))

        // Then correct views are displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.message_view)).check(matches(not(isDisplayed())))

        // Snackbar is shown with onRefresh button
        onView(allOf(withId(R.id.snackbar_text), withText(errorMessage))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.snackbar_action), withText(R.string.action_retry))).check(matches(isDisplayed()))
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
//        onView(withHint(R.string.create_favorite_code_hint)).check(matches(isDisplayed()))
//        onView(withHint(R.string.create_favorite_title_hint)).check(matches(isDisplayed()))
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
        val resultsList = TestModelsFactory.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // Then check all items
        for (favoriteView in resultsList.withIndex()) {
            // Scroll to item favoriteView.index
            onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<FavoritesAdapter.FavoriteViewHolder>(favoriteView.index))
            // Check item is displayed correctly
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].code.toString()))))
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].latestEta))))
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index)).check(matches(hasDescendant(withText(resultsList[favoriteView.index].originalText))))
        }
    }

    @Test
    fun whenAcceptingRequestStateIsFalseRequestingTextIsShown() {
        // Given (make sure requesting is not being shown)
        requestsAvailable.postValue(true)
        // When
        requestsAvailable.postValue(false)
        // Then Snackbar with requesting text and cancel action are shown
        onView(allOf(withId(R.id.snackbar_text), withText(R.string.info_requesting))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.snackbar_action), withText(R.string.action_cancel))).check(matches(isDisplayed()))
    }

    @Test
    fun whenAcceptingRequestStateIsTrueSnackbarWithCancelActionIsDismissed() {
        // Given (make sure requesting is showing)
        requestsAvailable.postValue(false)
        // When
        requestsAvailable.postValue(true)
        // Then Snackbar with requesting text and cancel action are not shown
        onView(allOf(withId(R.id.snackbar_text), withText(R.string.info_requesting))).check(doesNotExist())
        onView(allOf(withId(R.id.snackbar_action), withText(R.string.action_cancel))).check(doesNotExist())
    }

    @Test
    fun whenAcceptingRequestStateIsFalseRequestSmsButtonsAreDisabled() {
        // Given
        val resultsList = TestModelsFactory.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // When
        requestsAvailable.postValue(false)
        // Then ALL request ETA buttons are disabled
        favoritesFragment.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view)?.let {
            RecyclerViewMatcher.waitForAdapterChange(it)
            for (favoriteView in resultsList.withIndex()) {
                // Scroll to item at favoriteView.index
                onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<FavoritesAdapter.FavoriteViewHolder>(favoriteView.index))
                // Check item is displayed correctly
                onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index))
                        .check(matches(hasDescendant(withId(R.id.eta_button)))).check(matches(not(isEnabled())))
            }
        }
    }

    @Test
    fun whenAcceptingRequestStateIsTrueRequestSmsButtonsAreEnabled() {
        // Given
        val resultsList = TestModelsFactory.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // When
        requestsAvailable.postValue(false)
        // Then ALL request ETA buttons are enabled
        favoritesFragment.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view)?.let {
            RecyclerViewMatcher.waitForAdapterChange(it)
            for (favoriteView in resultsList.withIndex()) {
                // Scroll to item at favoriteView.index
                onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<FavoritesAdapter.FavoriteViewHolder>(favoriteView.index))
                // Check item is displayed correctly
                onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(favoriteView.index))
                        .check(matches(hasDescendant(withId(R.id.eta_button)))).check(matches(isEnabled()))
            }
        }
    }

    @Test
    @Ignore("ViewModel mocking not at 100% -> https://stackoverflow.com/questions/49833533/how-to-correctly-mock-viewmodel-on-androidtest")
    fun whenRequestButtonIsClickedViewModelRequestIsCalled() {
        // Given
        val resultsList = TestModelsFactory.generateFavoriteViewList()
        results.postValue(Resource.success(resultsList))
        // When
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition<FavoritesAdapter.FavoriteViewHolder>(0))

        onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText(R.string.action_send_sms)))).perform(click())
        // Check requestEta was called
        verify(viewModel).onEtaRequested(resultsList[0])
    }

    @Test
    @Ignore("ViewModel mocking not at 100% -> https://stackoverflow.com/questions/49833533/how-to-correctly-mock-viewmodel-on-androidtest")
    fun whenPullToRefreshIsUsedViewIsInLoadingState() {

    }

    @Test
    @Ignore("ViewModel mocking not at 100% -> https://stackoverflow.com/questions/49833533/how-to-correctly-mock-viewmodel-on-androidtest")
    fun whenPullToRefreshIsUsedViewModelRetryIsCalled() {

    }

}