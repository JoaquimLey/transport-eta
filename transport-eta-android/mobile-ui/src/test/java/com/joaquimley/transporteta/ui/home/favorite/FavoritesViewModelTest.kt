package com.joaquimley.transporteta.ui.home.favorite

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.model.SmsModel
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.ui.model.data.ResourceState
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelImpl
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock private val smsController = mock(SmsController::class.java)
    @Mock private lateinit var mockFavoriteViewObserver: Observer<Resource<List<FavoriteView>>>

//    private lateinit var captor: KArgumentCaptor<SmsController>
    private lateinit var smsTestObserver: TestObserver<SmsModel>
    private lateinit var favoritesViewModel: FavoritesViewModelImpl

    @Before
    fun setUp() {
        `when`(smsController.observeIncomingSms()).thenReturn(just(SmsModel(Random().nextInt(), UUID.randomUUID().toString())))
        favoritesViewModel = FavoritesViewModelImpl(smsController)
    }

    @After
    fun tearDown() {

    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta is requested correct favorite code is passed to smsController`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(smsController, times(1)).requestEta(favoriteView.code)
    }

    @Test
    fun `fetch eta triggers loading state`() {
        // given
        favoritesViewModel.getFavourites().observeForever(mockFavoriteViewObserver)
        // when
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        assertEquals(favoritesViewModel.getFavourites().value?.status, ResourceState.LOADING)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received triggers success state`() {
        // given
        val testSms = SmsModel(Random().nextInt(), UUID.randomUUID().toString())
        // when
        `when`(smsController.observeIncomingSms()).thenReturn(just(testSms))
        // then
        assertEquals(favoritesViewModel.getFavourites().value?.status, ResourceState.SUCCESS)
        assertEquals(favoritesViewModel.getFavourites().value?.data, )

    }

    @Ignore("Ignored test: when sms is received correct data is passed -> Lacking implementation")
    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received correct data is passed`() {
        // given
//        captor.firstValue.onSuccess(SmsModel(Random().nextInt(), UUID.randomUUID().toString()))
//        favoritesViewModel.getFavourites().observeForever(mockFavoriteViewObserver)
//        smsTestObserver = mockSmsController.observeIncomingSms().test()
        // when
        favoritesViewModel.getFavourites()
        // then

    }

//    @Test
//    @Throws(Exception::class)
//    fun `favorites observable correctly emitted values`() {
//        val testSubscriber = TestSubscriber<Int>()
//        Observable.just(1).subscribe(testSubscriber)
//        testSubscriber.assertNoErrors()
//        testSubscriber.assertReceivedOnNext(Arrays.asList(1))
//    }
}