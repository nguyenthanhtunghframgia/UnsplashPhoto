package com.example.nguyenthanhtungh.unsplashphoto.ui.collection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nguyenthanhtungh.domain.usecase.collection.CollectionUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.InsertHistoryUseCase
import com.example.nguyenthanhtungh.domain.usecase.history.LimitHistoryUseCase
import com.example.nguyenthanhtungh.unsplashphoto.RxSchedulersOverrideRule
import com.example.nguyenthanhtungh.unsplashphoto.mock
import com.example.nguyenthanhtungh.unsplashphoto.model.CollectionItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.CoverPhotoItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.HistoryItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.model.UrlsItemMapper
import com.example.nguyenthanhtungh.unsplashphoto.rx.AppSchedulerProvider
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CollectionViewModelTest {

    @Rule
    @JvmField
    val rxSchedulersOverrideRule: RxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var collectionViewModel: CollectionViewModel

    private val appSchedulerProvider = AppSchedulerProvider()

    private val historyItemMapper = HistoryItemMapper()

    @Mock
    lateinit var insertHistoryUseCase: InsertHistoryUseCase

    @Mock
    lateinit var limitHistoryUseCase: LimitHistoryUseCase

    private val urlsItemMapper = UrlsItemMapper()
    private val coverPhotoItemMapper = CoverPhotoItemMapper(urlsItemMapper)
    private val collectionItemMapper = CollectionItemMapper(coverPhotoItemMapper)

    @Mock
    private lateinit var collectionUseCase: CollectionUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        collectionViewModel = CollectionViewModel(
            historyItemMapper, insertHistoryUseCase, limitHistoryUseCase,
            collectionItemMapper, collectionUseCase, appSchedulerProvider
        )
    }

    @Test
    fun isEmpty() {
        given(insertHistoryUseCase.createObservable(InsertHistoryUseCase.Param("query"))).willReturn(
            Single.just(1L)
        )
        val observer = mock<Observer<Boolean>>()
        collectionViewModel.isInsertComplete.observeForever(observer)
        collectionViewModel.insertHistory("query")
        assertEquals(collectionViewModel.isInsertComplete.value, true)
    }
}
