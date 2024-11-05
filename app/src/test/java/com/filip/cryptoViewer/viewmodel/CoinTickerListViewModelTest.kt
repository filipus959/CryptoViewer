package com.filip.cryptoViewer.viewmodel

import app.cash.turbine.test
import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.domain.usecase.SortAndFilterCoinsUseCase
import com.filip.cryptoViewer.presentation.ui.screens.cointickerlist.CoinTickerListViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinTickerListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val coinRepository: CoinRepository = mockk(relaxUnitFun = true)
    private val sortAndFilterCoinsUseCase: SortAndFilterCoinsUseCase = mockk()
    private lateinit var viewModel: CoinTickerListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        every { coinRepository.observeTickerCoins() } returns flowOf(
            listOf(
                CoinTickerItem("1", "Bitcoin", 60000.0, -1.5, 1, "BTC"),
                CoinTickerItem("2", "Ethereum", 4000.0, 2.0, 2, "ETH"),
            ),
        )

        every { sortAndFilterCoinsUseCase.filterCoinList(any(), any()) } returns listOf(
            CoinTickerItem("1", "Bitcoin", 60000.0, -1.5, 1, "BTC"),
            CoinTickerItem("2", "Ethereum", 4000.0, 2.0, 2, "ETH"),
        )

        every { sortAndFilterCoinsUseCase.sortCoins(any(), any()) } returns listOf(
            CoinTickerItem("1", "Bitcoin", 60000.0, -1.5, 1, "BTC"),
            CoinTickerItem("2", "Ethereum", 4000.0, 2.0, 2, "ETH"),
        )

        viewModel = CoinTickerListViewModel(
            coinRepository = coinRepository,
            sortAndFilterCoinsUseCase = sortAndFilterCoinsUseCase,
            ioDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `when initialized, state is loading and then success`() = runTest {
        viewModel.state.test {
            skipItems(1) // skip initial empty state

            val initialLoadingState = awaitItem()
            assertThat(initialLoadingState.isLoading).isTrue()

            val successState = awaitItem()
            assertThat(successState.isLoading).isFalse()
            assertThat(successState.coins).isNotEmpty()
            assertThat(successState.coins[0].name).isEqualTo("Bitcoin")
            assertThat(successState.coins[1].name).isEqualTo("Ethereum")

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when sort criteria updated, state updates with sorted list`() = runTest {
        viewModel.state.test {
            skipItems(1)
            awaitItem()

            viewModel.updateSortCriteria(SortField.RANK)

            val updatedState = awaitItem()
            assertThat(updatedState.coins).isNotEmpty()
            assertThat(updatedState.coins[0].name).isEqualTo("Bitcoin")
            assertThat(updatedState.coins[1].name).isEqualTo("Ethereum")

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `when search query updated, state updates with filtered list`() = runTest {
        every { sortAndFilterCoinsUseCase.filterCoinList("Ethereum", any()) } returns listOf(
            CoinTickerItem("2", "Ethereum", 4000.0, 2.0, 2, "ETH"),
        )

        every { sortAndFilterCoinsUseCase.sortCoins(any(), any()) } returns listOf(
            CoinTickerItem("2", "Ethereum", 4000.0, 2.0, 2, "ETH"),
        )

        viewModel.state.test {
            skipItems(1)
            awaitItem()

            viewModel.onSearchQueryUpdate("Ethereum")

            val filteredState = awaitItem()
            assertThat(filteredState.coins.size).isEqualTo(1)
            assertThat(filteredState.coins[0].name).isEqualTo("Ethereum")

            cancelAndConsumeRemainingEvents()
        }
    }
}
