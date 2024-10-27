package com.filip.cryptoViewer.domain.usecase

import com.filip.cryptoViewer.domain.model.CoinTickerItem
import com.filip.cryptoViewer.domain.model.SortCriteria
import com.filip.cryptoViewer.domain.model.SortField
import com.filip.cryptoViewer.domain.model.SortOrder
import javax.inject.Inject

class SortAndFilterCoinsUseCase @Inject constructor() {

    fun filterCoinList(query: String, coins: List<CoinTickerItem>): List<CoinTickerItem> {
        return if (query.isBlank()) coins else coins.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun sortCoins(coins: List<CoinTickerItem>, criteria: SortCriteria): List<CoinTickerItem> {
        var sortedList = when (criteria.field) {
            SortField.PRICE -> coins.sortedBy { it.usdPrice }
            SortField.RANK -> coins.sortedBy { it.rank }.reversed()
            SortField.CHANGE -> coins.sortedBy { it.percentChange24h }
        }
        if (criteria.order == SortOrder.DESCENDING) {
            sortedList = sortedList.reversed()
        }
        return sortedList
    }
}
