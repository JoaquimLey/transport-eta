package com.joaquimley.transporteta.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.joaquimley.transporteta.domain.interactor.favorites.ClearAllTransportsAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.GetFavoritesUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.favorites.MarkTransportAsNoFavoriteUseCase
import com.joaquimley.transporteta.domain.interactor.transport.CancelEtaRequestUseCase
import com.joaquimley.transporteta.domain.interactor.transport.RequestEtaUseCase
import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.presentation.data.Resource
import com.joaquimley.transporteta.presentation.data.ResourceState
import com.joaquimley.transporteta.presentation.home.favorite.FavoritesViewModelImpl
import com.joaquimley.transporteta.presentation.mapper.TransportMapper
import com.joaquimley.transporteta.presentation.model.TransportView
import com.joaquimley.transporteta.presentation.util.factory.TransportFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class FavoritesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val robot = Robot()

    private val mockGetFavoritesUseCase = mock<GetFavoritesUseCase>()
    private val mockMarkTransportAsFavoriteUseCase = mock<MarkTransportAsFavoriteUseCase>()
    private val mockMarkTransportAsNoFavoriteUseCase = mock<MarkTransportAsNoFavoriteUseCase>()
    private val mockClearAllTransportsAsFavoriteUseCase = mock<ClearAllTransportsAsFavoriteUseCase>()
    private val mockRequestEtaUseCase = mock<RequestEtaUseCase>()
    private val mockCancelEtaRequestUseCase = mock<CancelEtaRequestUseCase>()
    private val mockMapper = mock<TransportMapper>()

//    private val favoritesMockObserver = mock<Observer<Resource<List<TransportView>>>>()
    private val viewModel = FavoritesViewModelImpl(mockGetFavoritesUseCase,
    mockMarkTransportAsFavoriteUseCase,
    mockMarkTransportAsNoFavoriteUseCase,
    mockClearAllTransportsAsFavoriteUseCase,
    mockRequestEtaUseCase,
    mockCancelEtaRequestUseCase,
    mockMapper)

    @Before
    fun setup() {
//        viewModel = FavoritesViewModelImpl(mockGetFavoritesUseCase,
//                mockMarkTransportAsFavoriteUseCase,
//                mockMarkTransportAsNoFavoriteUseCase,
//                mockClearAllTransportsAsFavoriteUseCase,
//                mockRequestEtaUseCase,
//                mockCancelEtaRequestUseCase,
//                mockMapper)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getFavoritesExecutesGetFavoritesUseCase() {
        // Assemble
        robot.stubSuccessGetFavoritesUseCase()
        // Act
        viewModel.getFavorites()
        // Assert
        verify(mockGetFavoritesUseCase, atLeast(1)).execute(anyOrNull())
    }

    @Test
    fun getFavoritesTriggersLoadingState() {
        // Assemble
        robot.stubSuccessGetFavoritesUseCase()
        // Act
        viewModel.getFavorites()
        // Assert
        verify(mockGetFavoritesUseCase, atLeast(1)).execute(anyOrNull())
    }

    @Test
    fun getFavoritesReturnsCorrectStateOnSuccess() {
        // Assemble
        robot.stubSuccessGetFavoritesUseCase()
        // Act
        viewModel.getFavorites()
        // Assert
        assert(viewModel.getFavorites().value?.status == ResourceState.SUCCESS)
        // Fail reason
        { "Status was ${viewModel.getFavorites().value?.status}\n Instead of: SUCCESS" }
    }

    @Test
    fun getFavoritesReturnsDataOnSuccess() {
        // Assemble
        val useCaseData = TransportFactory.makeTransportList(5)
        val stubbedMapperDataList = TransportFactory.makeTransportViewList(5)
        robot.stubMapper(useCaseData, stubbedMapperDataList, true)
        // Act
        viewModel.getFavorites()
        // Assert
        assert(viewModel.getFavorites().value?.data == stubbedMapperDataList)
        // Fail reason
        { "Data was \n${viewModel.getFavorites().value?.data}\n instead of \n$stubbedMapperDataList" }
    }

    @Test
    fun getFavoritesReturnsNoErrorMessageOnSuccess() {
        // Assemble
        robot.stubSuccessGetFavoritesUseCase()
        // Act
        viewModel.getFavorites()
        // Assert
        assert(viewModel.getFavorites().value?.message == null)
        // Fail reason
        { "Message was ${viewModel.getFavorites().value?.message} instead of null" }
    }

    /**
     * End of tests
     */

    inner class Robot {
        fun stubSuccessGetFavoritesUseCase(flowable: List<Transport>? = null, count: Int = 3): List<Transport> {
            // Assemble
            val testData = flowable ?: TransportFactory.makeTransportList(count)
            stubExecuteGetFavoritesUseCase(Flowable.just(testData))
            return testData
        }

        fun stubMapper(transportList: List<Transport>, transportViewList: List<TransportView>, isStubGetFavoritesUseCase: Boolean = false) {
            for (transport in transportList.withIndex()) {
                stubTransportMapperToView(transport.value, transportViewList[transport.index])
            }

            if (isStubGetFavoritesUseCase) {
                robot.stubSuccessGetFavoritesUseCase(transportList)
            }
        }

        fun stubMapperToModel(transportViewList: List<TransportView>, transportList: List<Transport>, isStubGetFavoritesUseCase: Boolean = false) {
            for (transportView in transportViewList.withIndex()) {
                stubTransportMapperToModel(transportView.value, transportList[transportView.index])
            }

            if (isStubGetFavoritesUseCase) {
                robot.stubSuccessGetFavoritesUseCase(transportList)
            }
        }

        // Private robot implementations
        private fun stubTransportMapperToView(transport: Transport, transportView: TransportView) {
            whenever(mockMapper.toView(transport)).thenReturn(transportView)
        }

        private fun stubTransportMapperToModel(transportView: TransportView, transport: Transport) {
            whenever(mockMapper.toModel(transportView)).thenReturn(transport)
        }

        private fun stubExecuteGetFavoritesUseCase(flowable: Flowable<List<Transport>>) {
            Mockito.`when`(mockGetFavoritesUseCase.execute(anyOrNull())).thenReturn(flowable)
        }
    }

//    @Test
//    fun `fetch eta triggers not accepting requests state`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        // wMockitoAnnotations.initMocks(this)hen
//        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
//        favoritesViewModel.onEtaRequested(favoriteView)
//        // then
//        verify(requestStatusObserver).onChanged(false)
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when favorite eta request is canceled accepting requests is true`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        favoritesViewModel.onEtaRequested(favoriteView)
//        // when
//        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
//        favoritesViewModel.onEtaRequestCanceled()
//        // then
//        verify(requestStatusObserver).onChanged(true)
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when favorite eta request is canceled smsController invalidate request is called`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        favoritesViewModel.onEtaRequested(favoriteView)
//        // when
//        favoritesViewModel.onEtaRequestCanceled()
//        // then
//        verify(smsController, times(1)).invalidateRequest()
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when favorite eta is requested smsController requestEta is called`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        // when
//        favoritesViewModel.onEtaRequested(favoriteView)
//        // then
//        verify(smsController, times(1)).requestEta(anyInt())
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when favorite eta is requested correct favorite code is passed to smsController`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        // when
//        favoritesViewModel.onEtaRequested(favoriteView)
//        verify(smsController).requestEta(captor.capture())
//        // then
//        assertThat(captor.firstValue, `is`(favoriteView.code))
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when sms is received triggers accepting requests state`() {
//        // given
//        val favoriteView = TransportView(Random().nextInt(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
//        favoritesViewModel.onEtaRequested(favoriteView)
//        // when
//        favoritesViewModel.isAcceptingRequests().observeForever(requestStatusObserver)
//        smsResult.onNext(TestModelsFactory.generateSmsModel())
//        // then
//        verify(requestStatusObserver).onChanged(true)
//    }
//
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when sms is received correct data is passed`() {
//        // given
////        captor.firstValue.onSuccess(SmsModel(Random().nextInt(), UUID.randomUUID().toString()))
////        favoritesViewModel.getFavorites().observeForever(mockFavoriteViewObserver)
////        smsTestObserver = mockSmsController.observeIncomingSms().test()
//        // when
////        favoritesViewModel.getFavorites()
//        // then
//
//
//        /**
//
//        val list = BufferooFactory.makeBufferooList(2)
//        val viewList = BufferooFactory.makeBufferooViewList(2)
//        stubBufferooMapperMapToView(viewList[0], list[0])
//        stubBufferooMapperMapToView(viewList[1], list[1])
//
//        bufferoosViewModel.getBufferoos()
//
//        verify(getBufferoos).execute(captor.capture(), eq(null))
//        captor.firstValue.onNext(list)
//
//        assert(bufferoosViewModel.getBufferoos().value?.status == ResourceState.SUCCESS)
//
//
//
//         */
//        // given
//        favoritesViewModel.getFavorites().observeForever(observer)
//        val code = DataFactory.randomInt()
//        favoritesViewModel.onEtaRequested(TestModelsFactory.generateFavoriteView(code))
//        // when
//        smsResult.onNext(TestModelsFactory.generateSmsModel(code))
//        // then
////        prin("The data is ${favoritesViewModel.getFavorites().value?.data}")
//        assert(favoritesViewModel.getFavorites().value?.data?.any { it.code == code } ?: false)
//    }
//
//    @Ignore("Ignored test: when sms is received correct data is passed -> Lacking implementation")
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when sms is received single is completed`() {
//
//    }
//
//    @Ignore("Ignored test: implement test")
//    @Test
//    @Throws(IllegalArgumentException::class)
//    fun `when sms request errors the error state posted`() {
//
//    }

//    @Test
//    @Throws(Exception::class)
//    fun `favorites observable correctly emitted values`() {
//        val testSubscriber = TestSubscriber<Int>()
//        Observable.just(1).subscribe(testSubscriber)
//        testSubscriber.assertNoErrors()
//        testSubscriber.assertReceivedOnNext(Arrays.asList(1))
//    }
}