package com.example.take_my_money.ui.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.take_my_money.ui.utils.Constants
import java.io.Serializable

@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val asset_id: String,
    val name: String,
    val volume_1hrs_usd: Double,
    val volume_1day_usd: Double,
    val volume_1mth_usd: Double,
    val price_usd: Double,
    val data_end: String,
    val id_icon: String
) : Serializable {

    fun getPathUrlImage(): String {
        return Constants.BASE_URL_IMAGE.plus(getCoinFileName())
    }

    fun getCoinFileName(): String {
        return "${id_icon.replace("-", "")}.png"
    }
}
