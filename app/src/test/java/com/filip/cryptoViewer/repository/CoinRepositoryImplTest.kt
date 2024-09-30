import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinExchangeDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
import com.filip.cryptoViewer.data.remote.CoinPaprikaApi
import com.filip.cryptoViewer.data.remote.dto.CoinChartDtoItem
import com.filip.cryptoViewer.data.remote.dto.CoinDetailDto
import com.filip.cryptoViewer.data.remote.dto.CoinDto
import com.filip.cryptoViewer.data.remote.dto.CoinExchangeDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import com.filip.cryptoViewer.data.remote.dto.Links
import com.filip.cryptoViewer.data.remote.dto.LinksExtended
import com.filip.cryptoViewer.data.remote.dto.Quotes
import com.filip.cryptoViewer.data.remote.dto.Stats
import com.filip.cryptoViewer.data.remote.dto.Tag
import com.filip.cryptoViewer.data.remote.dto.USD
import com.filip.cryptoViewer.data.remote.dto.Whitepaper
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.data.repository.implementation.CoinRepositoryImpl
import com.filip.cryptoViewer.domain.model.CoinExchange
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.whenever
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CoinRepositoryImplTest {

    private lateinit var repository: CoinRepositoryImpl
    private lateinit var api: CoinPaprikaApi
    private lateinit var coinTickerItemDao: CoinTickerItemDao
    private lateinit var coinDetailDao: CoinDetailDao
    private lateinit var coinChartDao: CoinChartDao
    private lateinit var coinExchangeDao: CoinExchangeDao

    private val mockCoinExchangeDto = CoinExchangeDto(
        amount = 1000,
        base_currency_id = "btc",
        base_currency_name = "Bitcoin",
        base_price_last_updated = "2023-09-30T10:15:30Z",
        price = 45000.75,
        quote_currency_id = "usd",
        quote_currency_name = "US Dollar",
        quote_price_last_updated = "2023-09-30T10:15:30Z"
    )

    private val mockCoinExchange = CoinExchange(
        price = 45000.75,
        coinId = "btc",
        coinId2 = "usd"
    )

    @Before
    fun setup() {
        api = mock(CoinPaprikaApi::class.java)
        coinTickerItemDao = mock(CoinTickerItemDao::class.java)
        coinDetailDao = mock(CoinDetailDao::class.java)
        coinChartDao = mock(CoinChartDao::class.java)
        coinExchangeDao = mock(CoinExchangeDao::class.java)
        repository =
            CoinRepositoryImpl(api, coinTickerItemDao, coinDetailDao, coinChartDao, coinExchangeDao)
    }

    @Test
    fun `test getCoinExchanges - success from API and cache`() = runTest {
        // Mock API and DAO behavior
        whenever(api.getCoinExchange("btc", "usd")).thenReturn(mockCoinExchangeDto)
        whenever(coinExchangeDao.getCoinExchange("btc", "usd")).thenReturn(
            mockCoinExchangeDto.toDbModel()
        )

        // Execute method
        val result = repository.getCoinExchanges("btc", "usd")

        // Verify the expected behavior
        verify(api).getCoinExchange("btc", "usd")
        verify(coinExchangeDao).insertCoinExchange(mockCoinExchangeDto.toDbModel())
        verify(coinExchangeDao).getCoinExchange("btc", "usd")

        // Validate the result
        assertEquals(mockCoinExchange, result)
    }

    @Test
    fun `test FetchTickerCoins - inserts Into Database`() = runBlocking {
        val mockTickerDtos = ArrayList<CoinTickerDto>(
            listOf(
                CoinTickerDto(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 1,
                    quotes = Quotes(
                        USD(
                            ath_date = "",
                            ath_price = 0.0,
                            market_cap = 0,
                            market_cap_change_24h = 0.0,
                            percent_change_24h = 0.0,
                            percent_change_1h = 0.0,
                            percent_change_7d = 0.0,
                            percent_change_30d = 0.0,
                            price = 30000.0,
                            volume_24h = 100000.0,
                            volume_24h_change_24h = 0.0,
                            percent_change_1y = 0.0,
                            percent_change_6h = 0.0,
                            percent_change_12h = 0.0,
                            percent_change_15m = 0.0,
                            percent_change_30m = 0.0,
                            percent_from_price_ath = 0.5
                        )
                    ),
                    first_data_at = "",
                    last_updated = "",
                    max_supply = 21000000,
                    total_supply = 19000000,
                    beta_value = 0.1
                ),
                CoinTickerDto(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    rank = 2,
                    quotes = Quotes(
                        USD(
                            ath_date = "",
                            ath_price = 0.0,
                            market_cap = 0,
                            market_cap_change_24h = 0.0,
                            percent_change_24h = 0.0,
                            percent_change_1h = 0.0,
                            percent_change_7d = 0.0,
                            percent_change_30d = 0.0,
                            price = 2000.0,
                            volume_24h = 50000.0,
                            volume_24h_change_24h = 0.0,
                            percent_change_1y = 0.0,
                            percent_change_6h = 0.0,
                            percent_change_12h = 0.0,
                            percent_change_15m = 0.0,
                            percent_change_30m = 0.0,
                            percent_from_price_ath = 0.5
                        )
                    ),
                    first_data_at = "",
                    last_updated = "",
                    max_supply = 10,
                    total_supply = 120000000,
                    beta_value = 0.01
                )
            )
        )


        `when`(api.getTickerCoins()).thenReturn(mockTickerDtos)

        repository.fetchTickerCoins()

        verify(coinTickerItemDao).insertAllCoinTickerItems(mockTickerDtos.map { it.toDbModel() } as ArrayList<CoinTickerItemEntity>)
    }


    @Test
    fun `test GetCoins - returns MappedCoins`() = runBlocking {
        val mockCoinsDto = listOf(
            CoinDto(
                id = "bitcoin",
                is_active = true,
                is_new = false,
                name = "Bitcoin",
                rank = 1,
                symbol = "BTC",
                type = "coin"
            ),
            CoinDto(
                id = "ethereum",
                is_active = true,
                is_new = false,
                name = "Ethereum",
                rank = 2,
                symbol = "ETH",
                type = "coin"
            )
        )
        `when`(api.getCoins()).thenReturn(mockCoinsDto)

        val result = repository.getCoins()

        assertEquals(mockCoinsDto.map { it.toCoin() }, result)
    }

    @Test
    fun `test GetCoinById - successfully Returns CoinDetail`() = runBlocking {
        val coinId = "bitcoin"
        val mockCoinDetail = CoinDetailDto(
            name = "Bitcoin",
            symbol = "BTC",
            description = "A decentralized digital currency.",
            rank = 1,
            tags = listOf(Tag(1, id = "", name = "", ico_counter = 1)),
            team = listOf(),
            id = coinId,
            first_data_at = "",
            is_active = false,
            development_status = "",
            hash_algorithm = "",
            links_extended = listOf(LinksExtended(type = "", url = "", stats = Stats(1, 1, 1, 1))),
            logo = "",
            type = "",
            proof_type = "",
            started_at = "",
            whitepaper = Whitepaper("", ""),
            last_data_at = "",
            org_structure = "",
            open_source = false,
            links = (Links(
                explorer = emptyList(),
                facebook = emptyList(),
                reddit = emptyList(),
                website = emptyList(),
                source_code = emptyList(),
                youtube = emptyList()
            )),
            is_new = false,
            message = "",
            hardware_wallet = true


        )
        `when`(api.getCoinById(coinId)).thenReturn(mockCoinDetail)
        `when`(coinDetailDao.getCoinDetailById(coinId)).thenReturn(mockCoinDetail.toDbModel())

        val result = repository.getCoinById(coinId)

        assertEquals(mockCoinDetail.toDbModel().toDomainModel(), result)
        verify(coinDetailDao).insertCoinDetail(mockCoinDetail.toDbModel())
    }

    @Test(expected = RuntimeException::class)
    fun `test GetCoinById - throws Exception On NoInternet And NoCache`(): Unit = runBlocking {
        val coinId = "bitcoin"
        `when`(api.getCoinById(coinId)).thenThrow(IOException())
        `when`(coinDetailDao.getCoinDetailById(coinId)).thenReturn(null)

        repository.getCoinById(coinId)

    }

    @Test
    fun `test ObserveTickerCoins returns FlowOfTickerItems`() = runBlocking {
        // Given
        val mockDbItems = listOf(
            CoinTickerItemEntity(
                id = "bitcoin", name = "Bitcoin", symbol = "BTC", rank = 1,
                usdPrice = 30000.0, percent_change_24h = 0.0,
                first_data_at = "", last_updated = "",
                max_supply = 21000000, beta_value = 1.0, total_supply = 19000000
            )
        )
        `when`(coinTickerItemDao.getAllCoinTickerItems()).thenReturn(flowOf(mockDbItems))

        val result = repository.observeTickerCoins().first()

        assertEquals(mockDbItems.map { it.toDomainModel() }, result)
    }

    @Test
    fun `test GetChartCoinById - successfully Returns ChartData`() = runBlocking {
        val coinId = "bitcoin"
        val currentDateMinus364Days: LocalDate = LocalDate.now().minusDays(364)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDateMinus364Days.format(formatter)
        val mockChartData = ArrayList<CoinChartDtoItem>(
            listOf(
                CoinChartDtoItem(
                    market_cap = 500000000,
                    price = 30000.0,
                    timestamp = "2023-09-22T00:00:00Z",
                    volume_24h = 10000000,
                    coinId = coinId
                )
            )
        )

        val mockEntity = mockChartData.map { it.toDbModel(coinId) }

        `when`(api.getChartCoin(coinId, formattedDate)).thenReturn(mockChartData)
        `when`(coinChartDao.getCoinChartById(coinId)).thenReturn(mockEntity)

        val result = repository.getChartCoinById(coinId)

        verify(coinChartDao).insertAllCoinCharts(mockEntity)
        verify(coinChartDao).getCoinChartById(coinId)

        assert(result == mockEntity.map { it.toDomainModel(coinId) })
    }

    @Test(expected = RuntimeException::class)
    fun `test GetChartCoinById - throws Exception On NoInternet And NoCache`(): Unit = runBlocking {
        val coinId = "bitcoin"
        `when`(api.getChartCoin(coinId, anyString())).thenThrow(IOException())
        `when`(coinChartDao.getCoinChartById(coinId)).thenReturn(emptyList())

        repository.getChartCoinById(coinId)

    }
}
