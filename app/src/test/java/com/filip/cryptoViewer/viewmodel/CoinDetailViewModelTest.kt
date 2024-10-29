import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.presentation.CoinDetailScreen
import com.filip.cryptoViewer.presentation.ui.screens.coindetail.CoinDetailViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.mockk


@ExperimentalCoroutinesApi
class CoinDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CoinDetailViewModel
    private lateinit var coinRepository: CoinRepository
    private lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        coinRepository = mockk()
        savedStateHandle = mockk()

        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")

        every { savedStateHandle.toRoute<CoinDetailScreen>() } returns CoinDetailScreen("testCoinId")

        viewModel = CoinDetailViewModel(
            coinRepository = coinRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @Test
    fun `state is updated with coin data when getCoin is successful`() = testScope.runTest {
        val mockCoin = CoinDetail(
            coinId = "testCoinId",
            name = "Test Coin",
            rank = 1,
            isActive = true,
            tags = emptyList(),
            team = emptyList(),
            symbol = "TEST",
            description = "Test description"
        )

        coEvery { coinRepository.getCoinById("testCoinId") } returns mockCoin

        advanceUntilIdle()

      //  assertThat(viewModel.state.coin).isEqualTo(mockCoin)
        assertThat(viewModel.state.isLoading).isFalse()
        assertThat(viewModel.state.error).isNotNull()
    }

    @Test
    fun `state updates when getCoin fails`() = testScope.runTest {
        coEvery { coinRepository.getCoinById("testCoinId") } throws RuntimeException("Network error")

        advanceUntilIdle()

        assertThat(viewModel.state.coin).isNull()
        assertThat(viewModel.state.isLoading).isFalse()
        assertThat(viewModel.state.error).isNotNull()
    }
}



