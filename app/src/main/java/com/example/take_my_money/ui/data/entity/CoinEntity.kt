package com.example.take_my_money.ui.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val asset_id: String,
    val name: String,
    val volume_1hrs_usd: String,
    val volume_1day_usd: String,
    val volume_1mth_usd: String,
    val price_usd: String,
    val data_end: String,
)
