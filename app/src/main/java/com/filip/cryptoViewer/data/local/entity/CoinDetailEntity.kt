package com.filip.cryptoViewer.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filip.cryptoViewer.data.remote.dto.Tag
import com.filip.cryptoViewer.data.remote.dto.TeamMember

@Entity(tableName = "coin_details")
data class CoinDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "coinId")
    val coinId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "rank")
    val rank: Int,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean,
    @ColumnInfo(name = "tags")
    val tags: List<Tag>,
    @ColumnInfo(name = "team")
    val team: List<TeamMember>
)
