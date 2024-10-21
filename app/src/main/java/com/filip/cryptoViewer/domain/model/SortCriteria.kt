package com.filip.cryptoViewer.domain.model


enum class SortField {
    PRICE, RANK, CHANGE
}

enum class SortOrder {
    ASCENDING, DESCENDING
}

data class SortCriteria(
    val field: SortField = SortField.PRICE,
    val order: SortOrder = SortOrder.ASCENDING
)
