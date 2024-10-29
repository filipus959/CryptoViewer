package com.filip.cryptoViewer.usecase

import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.model.SortCriteria
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.model.SortOrder
import com.filip.cryptoViewer.domain.usecase.SortAndFilterCoinsUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SortAndFilterCoinsUseCaseTest {

    private lateinit var useCase: SortAndFilterCoinsUseCase
    private lateinit var testCoins: List<CoinTickerItem>

    @Before
    fun setUp() {
        useCase = SortAndFilterCoinsUseCase()

        testCoins = listOf(
            CoinTickerItem(id = "1", name = "Bitcoin", usdPrice = 40000.0, percentChange24h = 2.5, rank = 1, symbol = "BTC"),
            CoinTickerItem(id = "2", name = "Ethereum", usdPrice = 3000.0, percentChange24h = -1.2, rank = 2, symbol = "ETH"),
            CoinTickerItem(id = "3", name = "Cardano", usdPrice = 1.5, percentChange24h = 0.5, rank = 3, symbol = "ADA")
        )
    }

    @Test
    fun `filterCoinList returns all coins when query is blank`() {
        val result = useCase.filterCoinList("", testCoins)
        assertEquals(testCoins, result)
    }

    @Test
    fun `filterCoinList returns coins that match the query case-insensitively`() {
        val result = useCase.filterCoinList("bit", testCoins)
        assertEquals(listOf(testCoins[0]), result)
    }

    @Test
    fun `filterCoinList returns empty list when query does not match any coin`() {
        val result = useCase.filterCoinList("xyz", testCoins)
        assertEquals(emptyList<CoinTickerItem>(), result)
    }

    @Test
    fun `sortCoins sorts by price in ascending order`() {
        val criteria = SortCriteria(field = SortField.PRICE, order = SortOrder.ASCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[2], testCoins[1], testCoins[0]), result)
    }

    @Test
    fun `sortCoins sorts by price in descending order`() {
        val criteria = SortCriteria(field = SortField.PRICE, order = SortOrder.DESCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[0], testCoins[1], testCoins[2]), result)
    }

    @Test
    fun `sortCoins sorts by rank in descending order`() {
        val criteria = SortCriteria(field = SortField.RANK, order = SortOrder.DESCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[0], testCoins[1], testCoins[2]), result)
    }

    @Test
    fun `sortCoins sorts by rank in ascending order`() {
        val criteria = SortCriteria(field = SortField.RANK, order = SortOrder.ASCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[2], testCoins[1], testCoins[0]), result)
    }

    @Test
    fun `sortCoins sorts by 24h percent change in ascending order`() {
        val criteria = SortCriteria(field = SortField.CHANGE, order = SortOrder.ASCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[1], testCoins[2], testCoins[0]), result)
    }

    @Test
    fun `sortCoins sorts by 24h percent change in descending order`() {
        val criteria = SortCriteria(field = SortField.CHANGE, order = SortOrder.DESCENDING)
        val result = useCase.sortCoins(testCoins, criteria)
        assertEquals(listOf(testCoins[0], testCoins[2], testCoins[1]), result)
    }
}