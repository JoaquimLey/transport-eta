package com.joaquimley.transporteta.ui.home.favorite

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelImpl
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.model.SmsModel
import com.joaquimley.transporteta.ui.model.data.ResourceState
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.test.assertEquals


@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var smsController: SmsController
    @Mock private lateinit var observer: Observer<Resource<List<FavoriteView>>>
    @Mock private lateinit var acceptingRequestsObserver: Observer<Boolean>

//    private lateinit var captor: KArgumentCaptor<SmsController>
    private lateinit var smsTestObserver: TestObserver<SmsModel>
    private lateinit var favoritesViewModel: FavoritesViewModelImpl

    private var testSmsModel = SmsModel(Random().nextInt(), UUID.randomUUID().toString())

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        `when`(smsController.requestEta(anyInt())).thenReturn(Single.just(testSmsModel))
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
    fun `fetch eta triggers not accepting requests state`() {
        // given
        favoritesViewModel.getAcceptingRequests().observeForever(acceptingRequestsObserver)
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(acceptingRequestsObserver).onChanged(false)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received triggers accepting requests state`() {
        favoritesViewModel.getAcceptingRequests().observeForever(acceptingRequestsObserver)
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val testSms = SmsModel(Random().nextInt(), UUID.randomUUID().toString())
        `when`(smsController.requestEta(favoriteView.code)).thenReturn(Single.just(testSms))
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(acceptingRequestsObserver).onChanged(true)
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