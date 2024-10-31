import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.filip.cryptoViewer.domain.model.CoinDetail
import com.filip.cryptoViewer.domain.repository.CoinRepository
import com.filip.cryptoViewer.presentation.CoinDetailScreen
import com.filip.cryptoViewer.presentation.ui.screens.coindetail.CoinDetailState
import com.filip.cryptoViewer.presentation.ui.screens.coindetail.CoinDetailViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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

@ExperimentalCoroutinesApi
class CoinDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CoinDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @MockK
    lateinit var coinRepository: CoinRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        savedStateHandle = mockk()

        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")

        every { savedStateHandle.toRoute<CoinDetailScreen>() } returns CoinDetailScreen("testCoinId")

        viewModel = CoinDetailViewModel(
            coinRepository = coinRepository,
            savedStateHandle = savedStateHandle,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @Test
    fun `state is updated with coin data when getCoin is successful`() = runTest(testDispatcher) {
        val mockCoin = CoinDetail(
            coinId = "testCoinId",
            name = "Test Coin",
            rank = 1,
            isActive = true,
            tags = emptyList(),
            team = emptyList(),
            symbol = "TEST",
            description = "Test description",
        )

        coEvery { coinRepository.getCoinById("testCoinId") } answers { mockCoin }
        // simulate view observing state, which triggers it's production

        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(
                CoinDetailState(isLoading = false, coin = null, error = ""),
            )
            assertThat(awaitItem()).isEqualTo(
                CoinDetailState(isLoading = true, coin = null, error = ""),
            )
            assertThat(awaitItem()).isEqualTo(
                CoinDetailState(isLoading = false, coin = mockCoin, error = ""),
            )
        }
    }

    @Test
    fun `state updates when getCoin fails`() = testScope.runTest {
        coEvery { coinRepository.getCoinById("testCoinId") } throws RuntimeException("Network error")

        advanceUntilIdle()

        assertThat(viewModel.state.value.coin).isNull()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNotNull()
    }
}
