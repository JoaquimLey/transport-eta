package com.joaquimley.transporteta.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelImpl
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.model.SmsModel
import com.joaquimley.transporteta.ui.testing.factory.TestFactoryFavoriteView
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var smsController: SmsController
    @Mock private lateinit var acceptingRequestsObserver: Observer<Boolean>
    @Mock private lateinit var observer: Observer<Resource<List<FavoriteView>>>
    @Captor private lateinit var argumentCaptor: ArgumentCaptor<Int>

    private lateinit var  captor: KArgumentCaptor<Int>

    private lateinit var favoritesViewModel: FavoritesViewModelImpl

    private var testSmsModel = SmsModel(Random().nextInt(), UUID.randomUUID().toString())

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        favoritesViewModel = FavoritesViewModelImpl(smsController)

        favoritesViewModel.getFavourites().observeForever(observer)
        favoritesViewModel.getAcceptingRequests().observeForever(acceptingRequestsObserver)
        captor = KArgumentCaptor(argumentCaptor, Int::class)
        `when`(smsController.requestEta(anyInt())).thenReturn(Single.just(testSmsModel))
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `fetch eta triggers not accepting requests state`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(acceptingRequestsObserver).onChanged(false)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta request is canceled accepting requests is true`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // when
        favoritesViewModel.cancelEtaRequest()
        // then
        verify(acceptingRequestsObserver, atLeastOnce()).onChanged(true)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta request is canceled smsController invalidate request is called`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // when
        favoritesViewModel.cancelEtaRequest()
        // then
        verify(smsController, times(1)).invalidateRequest()
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta is requested correct favorite code is passed to smsController`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        verify(smsController).requestEta(captor.capture())
        // then
        assertThat(captor.firstValue, `is`(favoriteView.code))
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta is requested correct smsController requestEta is called`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(smsController, times(1)).requestEta(anyInt())
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received triggers accepting requests state`() {

        /**

        val list = BufferooFactory.makeBufferooList(2)
        val viewList = BufferooFactory.makeBufferooViewList(2)
        stubBufferooMapperMapToView(viewList[0], list[0])
        stubBufferooMapperMapToView(viewList[1], list[1])

        bufferoosViewModel.getBufferoos()

        verify(getBufferoos).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(bufferoosViewModel.getBufferoos().value?.status == ResourceState.SUCCESS)



         */
        val results = TestFactoryFavoriteView.generateFavoriteViewList()

        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        val testSms = SmsModel(Random().nextInt(), UUID.randomUUID().toString())
        `when`(smsController.requestEta(favoriteView.code)).thenReturn(Single.just(testSms))
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then

//        verify(observer).onChanged()

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