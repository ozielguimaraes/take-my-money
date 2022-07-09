package com.example.take_my_money.domain.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.data.dao.FakeListCointEntity
import com.example.take_my_money.domain.usecases.UseCaseAllCoin
import com.example.take_my_money.domain.usecases.UseCaseDataSource
import com.example.take_my_money.presentation.viewmodel.CoinListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CoinListViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThread = newSingleThreadContext("UI thread")

    private val useCaseAllCoinTest = mockk<UseCaseAllCoin>()
    private val useCaseDataSourceTest = mockk<UseCaseDataSource>()

    private val observerLiveDataTest: Observer<List<CoinEntity>> = mockk(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThread)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThread.close()
    }

    @Test
    fun `when view model call requestApiListCoin checked functions of requestApi`() =
        runBlocking {
            // Given
            val viewModel = initViewModel()
            coEvery { useCaseAllCoinTest.getListCoin() } returns FakeListCointEntity().listAllCoins()

            // When
            viewModel.requestApiListCoin()

            // Then
            coVerify { useCaseAllCoinTest.getListCoin() }
        }

    @Test
    fun `when view model call requestApiListCoin checked functions of loadDataBase`() =
        runBlocking {
            // Given
            val viewModel = initViewModel()
            coEvery { useCaseAllCoinTest.getListCoin() } returns FakeListCointEntity().listAllCoins()

            // When
            viewModel.loadDataBase()

            // Then
            coVerify { useCaseDataSourceTest.getAllCoins() }
        }

    @Test
    fun `when view model fetches data from useCaseAllCoin checked the value of live data`() {
        // Given
        val viewModel = initViewModel()
        coEvery { useCaseAllCoinTest.getListCoin() } returns FakeListCointEntity().listAllCoins()
        // When
        viewModel.requestApiListCoin()
        /*delay(2000)*/
        val valueOfLiveData = viewModel.listCoinsLiveData.getOrAwaitValue()

        // Then
        Assert.assertEquals(FakeListCointEntity().listAllCoins(), valueOfLiveData)
    }

    private fun initViewModel(): CoinListViewModel {
        val viewModel = CoinListViewModel(useCaseAllCoinTest, useCaseDataSourceTest)
        viewModel.listCoinsLiveData.observeForever { observerLiveDataTest }
        return viewModel
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserver: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserver.invoke()

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set")
        }
    } finally {
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}
