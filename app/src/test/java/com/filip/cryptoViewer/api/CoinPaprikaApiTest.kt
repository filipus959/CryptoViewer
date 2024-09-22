import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.CoinChartDtoItem
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import com.filip.cryptoViewer.data.remote.dto.Links
import com.filip.cryptoViewer.data.remote.dto.LinksExtended
import com.filip.cryptoViewer.data.remote.dto.Quotes
import com.filip.cryptoViewer.data.remote.dto.Stats
import com.filip.cryptoViewer.data.remote.dto.Tag
import com.filip.cryptoViewer.data.remote.dto.TeamMember
import com.filip.cryptoViewer.data.remote.dto.USD
import com.filip.cryptoViewer.data.remote.dto.Whitepaper
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CoinPaprikaApiTest {

    @Mock
    lateinit var api: CoinPaprikaApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetCoinsReturnsListOfCoinDto() = runBlocking {
        // Given
        val mockCoins = listOf(
            CoinDto(id = "bitcoin", is_active = true, is_new = false, name = "Bitcoin", rank = 1, symbol = "BTC", type = "coin"),
            CoinDto(id = "ethereum", is_active = true, is_new = false, name = "Ethereum", rank = 2, symbol = "ETH", type = "coin")
        )
        Mockito.`when`(api.getCoins()).thenReturn(mockCoins)

        // When
        val result = api.getCoins()

        // Then
        assertEquals(mockCoins, result)
    }

    @Test
    fun testGetCoinByIdReturnsCoinDetailDto() = runBlocking {
        // Given
        val coinId = "bitcoin"
        val mockCoinDetail = CoinDetailDto(
            description = "A decentralized digital currency.",
            development_status = "active",
            first_data_at = "2013-04-28T00:00:00Z",
            hardware_wallet = true,
            hash_algorithm = "SHA-256",
            id = "bitcoin",
            is_active = true,
            is_new = false,
            last_data_at = "2023-09-22T00:00:00Z",
            links = Links(
                explorer = listOf("https://blockchain.info"),
                facebook = listOf("https://facebook.com/bitcoin"),
                reddit = listOf("https://reddit.com/r/Bitcoin"),
                source_code = listOf("https://github.com/bitcoin/bitcoin"),
                website = listOf("https://bitcoin.org"),
                youtube = listOf("https://youtube.com/bitcoin")
            ),
            links_extended = listOf(
                LinksExtended(stats = Stats(1,1,1,1), type = "website", url = "https://bitcoin.org")
            ),
            logo = "https://example.com/logo.png",
            message = "N/A",
            name = "Bitcoin",
            open_source = true,
            org_structure = "Decentralized",
            proof_type = "PoW",
            rank = 1,
            started_at = "2009-01-03T00:00:00Z",
            symbol = "BTC",
            tags = listOf(Tag(coin_counter = 1000, ico_counter = 50, id = "1", name = "Cryptocurrency")),
            team = listOf(TeamMember(id = "1", name = "Satoshi Nakamoto", position = "Founder")),
            type = "coin",
            whitepaper = Whitepaper(link = "https://bitcoin.org/bitcoin.pdf", thumbnail = "https://example.com/thumbnail.png")
        )
        Mockito.`when`(api.getCoinById(coinId)).thenReturn(mockCoinDetail)

        // When
        val result = api.getCoinById(coinId)

        // Then
        assertEquals(mockCoinDetail, result)
    }

    @Test
    fun testGetTickerCoinsReturnsListOfCoinTickerDto() = runBlocking {
        // Given
        val mockTickers = arrayListOf(
            CoinTickerDto(
                beta_value = 1.2,
                first_data_at = "2013-04-28T00:00:00Z",
                id = "bitcoin",
                last_updated = "2023-09-22T00:00:00Z",
                max_supply = 21000000,
                name = "Bitcoin",
                quotes = Quotes(
                    USD(
                        ath_date = "2021-11-10",
                        ath_price = 69000.0,
                        market_cap = 800000000000,
                        market_cap_change_24h = -1.5,
                        percent_change_12h = 2.5,
                        percent_change_15m = 0.5,
                        percent_change_1h = -0.2,
                        percent_change_1y = 300.0,
                        percent_change_24h = -1.0,
                        percent_change_30d = 10.0,
                        percent_change_30m = 0.0,
                        percent_change_6h = 0.5,
                        percent_change_7d = -5.0,
                        percent_from_price_ath = -27.5,
                        price = 50000.0,
                        volume_24h = 30000000000.0,
                        volume_24h_change_24h = -0.5
                    )
                ),
                rank = 1,
                symbol = "BTC",
                total_supply = 19000000
            ),
            CoinTickerDto(
                beta_value = 1.1,
                first_data_at = "2015-07-30T00:00:00Z",
                id = "ethereum",
                last_updated = "2023-09-22T00:00:00Z",
                max_supply = 0,
                name = "Ethereum",
                quotes = Quotes(
                    USD(
                        ath_date = "2021-11-10",
                        ath_price = 4800.0,
                        market_cap = 480000000000,
                        market_cap_change_24h = -2.0,
                        percent_change_12h = 3.0,
                        percent_change_15m = 0.8,
                        percent_change_1h = -0.1,
                        percent_change_1y = 100.0,
                        percent_change_24h = -2.5,
                        percent_change_30d = 8.0,
                        percent_change_30m = 0.1,
                        percent_change_6h = 1.0,
                        percent_change_7d = -4.0,
                        percent_from_price_ath = -10.0,
                        price = 4000.0,
                        volume_24h = 20000000000.0,
                        volume_24h_change_24h = -1.0
                    )
                ),
                rank = 2,
                symbol = "ETH",
                total_supply = 120000000
            )
        )
        Mockito.`when`(api.getTickerCoins()).thenReturn(mockTickers)

        // When
        val result = api.getTickerCoins()

        // Then
        assertEquals(mockTickers, result)
    }

    @Test
    fun testGetTickerCoinByIDReturnsCoinTickerDto() = runBlocking {
        // Given
        val coinId = "bitcoin"
        val mockTicker = CoinTickerDto(
            beta_value = 1.2,
            first_data_at = "2013-04-28T00:00:00Z",
            id = "bitcoin",
            last_updated = "2023-09-22T00:00:00Z",
            max_supply = 21000000,
            name = "Bitcoin",
            quotes = Quotes(
                USD(
                    ath_date = "2021-11-10",
                    ath_price = 69000.0,
                    market_cap = 800000000000,
                    market_cap_change_24h = -1.5,
                    percent_change_12h = 2.5,
                    percent_change_15m = 0.5,
                    percent_change_1h = -0.2,
                    percent_change_1y = 300.0,
                    percent_change_24h = -1.0,
                    percent_change_30d = 10.0,
                    percent_change_30m = 0.0,
                    percent_change_6h = 0.5,
                    percent_change_7d = -5.0,
                    percent_from_price_ath = -27.5,
                    price = 50000.0,
                    volume_24h = 30000000000.0,
                    volume_24h_change_24h = -0.5
                )
            ),
            rank = 1,
            symbol = "BTC",
            total_supply = 19000000
        )
        Mockito.`when`(api.getTickerCoinByID(coinId)).thenReturn(mockTicker)

        // When
        val result = api.getTickerCoinByID(coinId)

        // Then
        assertEquals(mockTicker, result)
    }

    @Test
    fun testGetChartCoinReturnsListOfCoinChartDtoItem() = runBlocking {
        // Given
        val coinId = "bitcoin"
        val startDate = "2023-01-01"
        val mockChart = arrayListOf(
            CoinChartDtoItem(
                market_cap = 800000000000,
                price = 50000.0,
                timestamp = "2023-01-01T00:00:00Z",
                volume_24h = 30000000000,
                coinId = "bitcoin"
            ),
            CoinChartDtoItem(
                market_cap = 810000000000,
                price = 51000.0,
                timestamp = "2023-01-02T00:00:00Z",
                volume_24h = 31000000000,
                coinId = "bitcoin"
            )
        )
        Mockito.`when`(api.getChartCoin(coinId, startDate)).thenReturn(mockChart)

        // When
        val result = api.getChartCoin(coinId, startDate)

        // Then
        assertEquals(mockChart, result)
    }
}

