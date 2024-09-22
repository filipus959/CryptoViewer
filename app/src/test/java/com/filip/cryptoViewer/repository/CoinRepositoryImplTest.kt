
import com.filip.cryptoViewer.data.local.dao.CoinChartDao
import com.filip.cryptoViewer.data.local.dao.CoinDetailDao
import com.filip.cryptoViewer.data.local.dao.CoinTickerItemDao
import com.filip.cryptoViewer.data.local.entity.CoinTickerItemEntity
import com.filip.cryptoViewer.data.local.mapper.toDbModel
import com.filip.cryptoViewer.data.local.mapper.toDomainModel
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
import com.filip.cryptoViewer.data.remote.dto.USD
import com.filip.cryptoViewer.data.remote.dto.Whitepaper
import com.filip.cryptoViewer.data.remote.dto.toCoin
import com.filip.cryptoViewer.data.repository.implementation.CoinRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CoinRepositoryImplTest {

    private lateinit var repository: CoinRepositoryImpl
    private lateinit var api: CoinPaprikaApi
    private lateinit var coinTickerItemDao: CoinTickerItemDao
    private lateinit var coinDetailDao: CoinDetailDao
    private lateinit var coinChartDao: CoinChartDao

    @Before
    fun setup() {
        api = mock(CoinPaprikaApi::class.java)
        coinTickerItemDao = mock(CoinTickerItemDao::class.java)
        coinDetailDao = mock(CoinDetailDao::class.java)
        coinChartDao = mock(CoinChartDao::class.java)

        repository = CoinRepositoryImpl(api, coinTickerItemDao, coinDetailDao, coinChartDao)
    }

    @Test
    fun testFetchTickerCoins_insertsIntoDatabase() = runBlocking {
        // Given
        val mockTickerDtos = ArrayList<CoinTickerDto>(listOf(
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
        ))

       // val mockTickerDos = mockTickerDtos.map { it.toDbModel() }

        `when`(api.getTickerCoins()).thenReturn(mockTickerDtos)

        // When
        repository.fetchTickerCoins()

        // Then
        verify(coinTickerItemDao).insertAllCoinTickerItems(mockTickerDtos.map { it.toDbModel() } as ArrayList<CoinTickerItemEntity>)
    }

//    @Test
//    fun fetchTickerCoins_httpException() = runBlocking {
//        // Mock API to throw an HttpException
//        `when`(api.getTickerCoins()).thenThrow(HttpException(mock()))
//
//        // Call the method
//        repository.fetchTickerCoins()
//        repository.observeTickerCoins()
//
//        // Verify that no data was inserted into the DAO
//        verify(coinTickerItemDao, never()).insertAllCoinTickerItems(any())
//    }



    @Test
    fun testGetCoins_returnsMappedCoins() = runBlocking {
        // Given
        val mockCoinsDto = listOf(
            CoinDto(id = "bitcoin", is_active = true, is_new = false, name = "Bitcoin", rank = 1, symbol = "BTC", type = "coin"),
            CoinDto(id = "ethereum", is_active = true, is_new = false, name = "Ethereum", rank = 2, symbol = "ETH", type = "coin")
        )
        `when`(api.getCoins()).thenReturn(mockCoinsDto)

        // When
        val result = repository.getCoins()

        // Then
        assertEquals(mockCoinsDto.map { it.toCoin() }, result)
    }

    @Test
    fun testGetCoinById_successfullyReturnsCoinDetail() = runBlocking {
        // Given
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
            links_extended = listOf(LinksExtended(type = "", url = "", stats = Stats(1,1,1,1))),
            logo = "",
            type ="" ,
            proof_type = "",
            started_at = "",
            whitepaper = Whitepaper("",""),
            last_data_at = "",
            org_structure = "",
            open_source = false,
            links = (Links(explorer = emptyList(), facebook = emptyList(), reddit = emptyList(), website = emptyList(), source_code = emptyList(), youtube = emptyList())),
            is_new = false,
            message = "",
            hardware_wallet = true



        )
        `when`(api.getCoinById(coinId)).thenReturn(mockCoinDetail)
        `when`(coinDetailDao.getCoinDetailById(coinId)).thenReturn(mockCoinDetail.toDbModel())

        // When
        val result = repository.getCoinById(coinId)

        // Then
        assertEquals(mockCoinDetail.toDbModel().toDomainModel(), result)
        verify(coinDetailDao).insertCoinDetail(mockCoinDetail.toDbModel())
    }

    @Test(expected = RuntimeException::class)
    fun testGetCoinById_throwsExceptionOnNoInternetAndNoCache(): Unit = runBlocking {
        // Given
        val coinId = "bitcoin"
        `when`(api.getCoinById(coinId)).thenThrow(IOException())
        `when`(coinDetailDao.getCoinDetailById(coinId)).thenReturn(null)

        // When
        repository.getCoinById(coinId)

        // Then: Expect RuntimeException
    }

    @Test
    fun testObserveTickerCoins_returnsFlowOfTickerItems() = runBlocking {
        // Given
        val mockDbItems = listOf(
            CoinTickerItemEntity(id = "bitcoin", name = "Bitcoin", symbol = "BTC", rank = 1,
                usdPrice = 30000.0, percent_change_24h = 0.0,
                first_data_at = "", last_updated = "",
                max_supply = 21000000, beta_value = 1.0, total_supply = 19000000)
        )
        `when`(coinTickerItemDao.getAllCoinTickerItems()).thenReturn(flowOf(mockDbItems))

        // When
        val result = repository.observeTickerCoins().first()

        // Then
        assertEquals(mockDbItems.map { it.toDomainModel() }, result)
    }

    @Test
    fun testGetChartCoinById_successfullyReturnsChartData() = runBlocking {
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

        // Mock API response
        `when`(api.getChartCoin(coinId, formattedDate)).thenReturn(mockChartData)

        // Mock DAO to return data when fetched by ID
        `when`(coinChartDao.getCoinChartById(coinId)).thenReturn(mockEntity)

        // Call the method
        val result = repository.getChartCoinById(coinId)

        // Verify DAO interactions
        verify(coinChartDao).insertAllCoinCharts(mockEntity)
        verify(coinChartDao).getCoinChartById(coinId)

        // Check the result
        assert(result == mockEntity.map { it.toDomainModel(coinId) })
    }

    @Test(expected = RuntimeException::class)
    fun testGetChartCoinById_throwsExceptionOnNoInternetAndNoCache(): Unit = runBlocking {
        // Given
        val coinId = "bitcoin"
        `when`(api.getChartCoin(coinId, anyString())).thenThrow(IOException())
        `when`(coinChartDao.getCoinChartById(coinId)).thenReturn(emptyList())

        // When
        repository.getChartCoinById(coinId)

        // Then: Expect RuntimeException
    }
}
