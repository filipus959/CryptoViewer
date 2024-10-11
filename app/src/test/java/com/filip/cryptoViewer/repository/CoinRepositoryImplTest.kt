package com.filip.cryptoViewer.repository

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
import com.filip.cryptoViewer.data.remote.dto.CoinExchangeDto
import com.filip.cryptoViewer.data.remote.dto.CoinTickerDto
import com.filip.cryptoViewer.data.remote.dto.Links
import com.filip.cryptoViewer.data.remote.dto.LinksExtended
import com.filip.cryptoViewer.data.remote.dto.Quotes
import com.filip.cryptoViewer.data.remote.dto.Stats
import com.filip.cryptoViewer.data.remote.dto.Tag
import com.filip.cryptoViewer.data.remote.dto.USD
import com.filip.cryptoViewer.data.remote.dto.Whitepaper
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
        baseCurrencyId = "btc",
        baseCurrencyName = "Bitcoin",
        basePriceLastUpdated = "2023-09-30T10:15:30Z",
        price = 45000.75,
        quoteCurrencyId = "usd",
        quoteCurrencyName = "US Dollar",
        quotePriceLastUpdated = "2023-09-30T10:15:30Z"
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
                            athDate = "",
                            athPrice = 0.0,
                            marketCap = 0,
                            marketCapChange24h = 0.0,
                            percentChange24h = 0.0,
                            percentChange1h = 0.0,
                            percentChange7d = 0.0,
                            percentChange30d = 0.0,
                            price = 30000.0,
                            volume24h = 100000.0,
                            volume24hChange24h = 0.0,
                            percentChange1y = 0.0,
                            percentChange6h = 0.0,
                            percentChange12h = 0.0,
                            percentChange15m = 0.0,
                            percentChange30m = 0.0,
                            percentFromPriceAth = 0.5
                        )
                    ),
                    firstDataAt = "",
                    lastUpdated = "",
                    maxSupply = 21000000,
                    totalSupply = 19000000,
                    betaValue = 0.1
                ),
                CoinTickerDto(
                    id = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    rank = 2,
                    quotes = Quotes(
                        USD(
                            athDate = "",
                            athPrice = 0.0,
                            marketCap = 0,
                            marketCapChange24h = 0.0,
                            percentChange24h = 0.0,
                            percentChange1h = 0.0,
                            percentChange7d = 0.0,
                            percentChange30d = 0.0,
                            price = 2000.0,
                            volume24h = 50000.0,
                            volume24hChange24h = 0.0,
                            percentChange1y = 0.0,
                            percentChange6h = 0.0,
                            percentChange12h = 0.0,
                            percentChange15m = 0.0,
                            percentChange30m = 0.0,
                            percentFromPriceAth = 0.5
                        )
                    ),
                    firstDataAt = "",
                    lastUpdated = "",
                    maxSupply = 10,
                    totalSupply = 120000000,
                    betaValue = 0.01
                )
            )
        )


        `when`(api.getTickerCoins()).thenReturn(mockTickerDtos)

        repository.fetchTickerCoins()

        verify(coinTickerItemDao).insertAllCoinTickerItems(mockTickerDtos.map { it.toDbModel() } as ArrayList<CoinTickerItemEntity>)
    }


    @Test
    fun `test GetCoinById - successfully Returns CoinDetail`() = runBlocking {
        val coinId = "bitcoin"
        val mockCoinDetail = CoinDetailDto(
            name = "Bitcoin",
            symbol = "BTC",
            description = "A decentralized digital currency.",
            rank = 1,
            tags = listOf(Tag(1, id = "", name = "", icoCounter = 1)),
            team = listOf(),
            id = coinId,
            firstDataAt = "",
            isActive = false,
            developmentStatus = "",
            hashAlgorithm = "",
            linksExtended = listOf(LinksExtended(type = "", url = "", stats = Stats(1, 1, 1, 1))),
            logo = "",
            type = "",
            proofType = "",
            startedAt = "",
            whitePaper = Whitepaper("", ""),
            lastDataAt = "",
            orgStructure = "",
            openSource = false,
            links = (Links(
                explorer = emptyList(),
                facebook = emptyList(),
                reddit = emptyList(),
                website = emptyList(),
                sourceCode = emptyList(),
                youtube = emptyList()
            )),
            isNew = false,
            message = "",
            hardwareWallet = true


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
                usdPrice = 30000.0, percentChange24h = 0.0,
                firstDataAt = "", lastUpdated = "",
                maxSupply = 21000000, betaValue = 1.0, totalSupply = 19000000
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
                    marketCap = 500000000,
                    price = 30000.0,
                    timestamp = "2023-09-22T00:00:00Z",
                    volume24h = 10000000,
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
