package com.joaquimley.transporteta.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.factory.TestModelsFactory
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelImpl
import com.joaquimley.transporteta.presentation.model.FavoriteView
import com.joaquimley.transporteta.sms.SmsController
import com.joaquimley.transporteta.sms.model.SmsModel
import com.joaquimley.transporteta.ui.testing.factory.ui.DataFactory
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor private lateinit var argumentCaptor: ArgumentCaptor<Int>
    @Mock private lateinit var observer: Observer<Resource<List<FavoriteView>>>
    @Mock private lateinit var smsController: SmsController
    @Mock private lateinit var requestStatusObserver: Observer<Boolean>

    private val smsResult: PublishSubject<SmsModel> = PublishSubject.create()

    private lateinit var captor: KArgumentCaptor<Int>
    private lateinit var favoritesViewModel: FavoritesViewModelImpl

    private val single = Single.create<SmsModel> { emitter ->
        smsResult.subscribe { emitter.onSuccess(it) }
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        captor = KArgumentCaptor(argumentCaptor, Int::class)
        `when`(smsController.requestEta(anyInt())).thenReturn(single)

        favoritesViewModel = FavoritesViewModelImpl(smsController)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `fetch eta triggers not accepting requests state`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(requestStatusObserver).onChanged(false)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when favorite eta request is canceled accepting requests is true`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // when
        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
        favoritesViewModel.cancelEtaRequest()
        // then
        verify(requestStatusObserver).onChanged(true)
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
    fun `when favorite eta is requested smsController requestEta is called`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        // when
        favoritesViewModel.onEtaRequested(favoriteView)
        // then
        verify(smsController, times(1)).requestEta(anyInt())
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
    fun `when sms is received triggers accepting requests state`() {
        // given
        val favoriteView = FavoriteView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
        favoritesViewModel.onEtaRequested(favoriteView)
        // when
        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
        smsResult.onNext(TestModelsFactory.generateSmsModel())
        // then
        verify(requestStatusObserver).onChanged(true)
    }

    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received correct data is passed`() {
        // given
//        captor.firstValue.onSuccess(SmsModel(Random().nextInt(), UUID.randomUUID().toString()))
//        favoritesViewModel.getFavorites().observeForever(mockFavoriteViewObserver)
//        smsTestObserver = mockSmsController.observeIncomingSms().test()
        // when
//        favoritesViewModel.getFavorites()
        // then


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
        // given
        favoritesViewModel.getFavorites().observeForever(observer)
        val code = DataFactory.randomInt()
        favoritesViewModel.onEtaRequested(TestModelsFactory.generateFavoriteView(code))
        // when
        smsResult.onNext(TestModelsFactory.generateSmsModel(code))
        // then
//        prin("The data is ${favoritesViewModel.getFavorites().value?.data}")
        assert(favoritesViewModel.getFavorites().value?.data?.any { it.code ==  code} ?: false)
    }

    @Ignore("Ignored test: when sms is received correct data is passed -> Lacking implementation")
    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms is received single is completed`() {

    }

    @Ignore("Ignored test: implement test")
    @Test
    @Throws(IllegalArgumentException::class)
    fun `when sms request errors the error state posted`() {

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