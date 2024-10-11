package com.filip.cryptoViewer.api

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
            developmentStatus = "active",
            firstDataAt = "2013-04-28T00:00:00Z",
            hardwareWallet = true,
            hashAlgorithm = "SHA-256",
            id = "bitcoin",
            isActive = true,
            isNew = false,
            lastDataAt = "2023-09-22T00:00:00Z",
            links = Links(
                explorer = listOf("https://blockchain.info"),
                facebook = listOf("https://facebook.com/bitcoin"),
                reddit = listOf("https://reddit.com/r/Bitcoin"),
                sourceCode = listOf("https://github.com/bitcoin/bitcoin"),
                website = listOf("https://bitcoin.org"),
                youtube = listOf("https://youtube.com/bitcoin")
            ),
            linksExtended = listOf(
                LinksExtended(stats = Stats(1,1,1,1), type = "website", url = "https://bitcoin.org")
            ),
            logo = "https://example.com/logo.png",
            message = "N/A",
            name = "Bitcoin",
            openSource = true,
            orgStructure = "Decentralized",
            proofType = "PoW",
            rank = 1,
            startedAt = "2009-01-03T00:00:00Z",
            symbol = "BTC",
            tags = listOf(Tag(coinCounter = 1000, icoCounter = 50, id = "1", name = "Cryptocurrency")),
            team = listOf(TeamMember(id = "1", name = "Satoshi Nakamoto", position = "Founder")),
            type = "coin",
            whitePaper = Whitepaper(link = "https://bitcoin.org/bitcoin.pdf", thumbnail = "https://example.com/thumbnail.png")
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
                betaValue = 1.2,
                firstDataAt = "2013-04-28T00:00:00Z",
                id = "bitcoin",
                lastUpdated = "2023-09-22T00:00:00Z",
                maxSupply = 21000000,
                name = "Bitcoin",
                quotes = Quotes(
                    USD(
                        athDate = "2021-11-10",
                        athPrice = 69000.0,
                        marketCap = 800000000000,
                        marketCapChange24h = -1.5,
                        percentChange12h = 2.5,
                        percentChange15m = 0.5,
                        percentChange1h = -0.2,
                        percentChange1y = 300.0,
                        percentChange24h = -1.0,
                        percentChange30d = 10.0,
                        percentChange30m = 0.0,
                        percentChange6h = 0.5,
                        percentChange7d = -5.0,
                        percentFromPriceAth = -27.5,
                        price = 50000.0,
                        volume24h = 30000000000.0,
                        volume24hChange24h = -0.5
                    )
                ),
                rank = 1,
                symbol = "BTC",
                totalSupply = 19000000
            ),
            CoinTickerDto(
                betaValue = 1.1,
                firstDataAt = "2015-07-30T00:00:00Z",
                id = "ethereum",
                lastUpdated = "2023-09-22T00:00:00Z",
                maxSupply = 0,
                name = "Ethereum",
                quotes = Quotes(
                    USD(
                        athDate = "2021-11-10",
                        athPrice = 4800.0,
                        marketCap = 480000000000,
                        marketCapChange24h = -2.0,
                        percentChange12h = 3.0,
                        percentChange15m = 0.8,
                        percentChange1h = -0.1,
                        percentChange1y = 100.0,
                        percentChange24h = -2.5,
                        percentChange30d = 8.0,
                        percentChange30m = 0.1,
                        percentChange6h = 1.0,
                        percentChange7d = -4.0,
                        percentFromPriceAth = -10.0,
                        price = 4000.0,
                        volume24h = 20000000000.0,
                        volume24hChange24h = -1.0
                    )
                ),
                rank = 2,
                symbol = "ETH",
                totalSupply = 120000000
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
            betaValue = 1.2,
            firstDataAt = "2013-04-28T00:00:00Z",
            id = "bitcoin",
            lastUpdated = "2023-09-22T00:00:00Z",
            maxSupply = 21000000,
            name = "Bitcoin",
            quotes = Quotes(
                USD(
                    athDate = "2021-11-10",
                    athPrice = 69000.0,
                    marketCap = 800000000000,
                    marketCapChange24h = -1.5,
                    percentChange12h = 2.5,
                    percentChange15m = 0.5,
                    percentChange1h = -0.2,
                    percentChange1y = 300.0,
                    percentChange24h = -1.0,
                    percentChange30d = 10.0,
                    percentChange30m = 0.0,
                    percentChange6h = 0.5,
                    percentChange7d = -5.0,
                    percentFromPriceAth = -27.5,
                    price = 50000.0,
                    volume24h = 30000000000.0,
                    volume24hChange24h = -0.5
                )
            ),
            rank = 1,
            symbol = "BTC",
            totalSupply = 19000000
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
                marketCap = 800000000000,
                price = 50000.0,
                timestamp = "2023-01-01T00:00:00Z",
                volume24h = 30000000000,
                coinId = "bitcoin"
            ),
            CoinChartDtoItem(
                marketCap = 810000000000,
                price = 51000.0,
                timestamp = "2023-01-02T00:00:00Z",
                volume24h = 31000000000,
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

