package com.filip.cryptoViewer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filip.cryptoViewer.data.remote.dto.TeamMember

@Entity(tableName = "coin_details")
data class CoinDetailEntity(
    @PrimaryKey
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>
)
