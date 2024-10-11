package com.filip.cryptoViewer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Quotes(
    @SerializedName("USD")
    val usd: USD
)