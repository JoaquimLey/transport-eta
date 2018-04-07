package com.joaquimley.transporteta

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.joaquimley.transporteta.home.favorite.FavoritesViewModel
import com.joaquimley.transporteta.model.FavoriteView
import com.joaquimley.transporteta.model.data.Resource
import com.joaquimley.transporteta.model.data.ResourceState
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.model.SmsModel
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var smsController: SmsController
    @Mock
    private lateinit var observer: Observer<Resource<List<FavoriteView>>>

    private lateinit var smsTestObserver: TestObserver<SmsModel>
    private lateinit var favoritesViewModel: FavoritesViewModel

    @Before
    fun setUp() {
        smsTestObserver = smsController.observeIncomingSms().test()
        `when`(smsController.observeIncomingSms()).thenReturn(just(SmsModel(Random().nextInt(), UUID.randomUUID().toString())))
        favoritesViewModel = FavoritesViewModel(smsController)
    }

    @After
    fun tearDown() {

    }


//    @Test
//    @Throws(Exception::class)
//    fun `favorites observable correctly emitted values`() {
//        val testSubscriber = TestSubscriber<Int>()
//        Observable.just(1).subscribe(testSubscriber)
//        testSubscriber.assertNoErrors()
//        testSubscriber.assertReceivedOnNext(Arrays.asList(1))
//    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received triggers success state`() {
//        // given
//        favoritesViewModel.getFavourites().observeForever(observer)
//        favoritesViewModel.getFavourites()
//        smsTestObserver = smsController.observeIncomingSms().test()
//        // when
//        smsTestObserver.onNext(SmsModel(Random().nextInt(), UUID.randomUUID().toString()))
        // then
        assertEquals(favoritesViewModel.getFavourites().value?.status, ResourceState.SUCCESS)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received correct data is passed`() {
        // given
        favoritesViewModel.getFavourites().observeForever(observer)
        smsTestObserver = smsController.observeIncomingSms().test()
        // when
        favoritesViewModel.getFavourites()
        // then
        assertEquals(favoritesViewModel.getFavourites().value?.data?.sortedBy { it.code }, favoriteViewList.toList().sortedBy { it.code })
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta is requested, correct favorite code is passed to smsController`() {
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
        favoritesViewModel.getFavourites().observeForever(observer)
        // when
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        assertEquals(favoritesViewModel.getFavourites().value?.status, ResourceState.LOADING)
    }
}