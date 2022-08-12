package com.example.take_my_money.data.dao

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.take_my_money.presentation.utils.Constants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "coin",
    indices = [
        Index(value = ["nameCurrency", "currencyAbbreviation"], unique = true)
    ]
)
data class CoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("asset_id")
    val currencyAbbreviation: String? = "",

    @SerializedName("name")
    val nameCurrency: String? = "",

    @SerializedName("type_is_crypto")
    val typeOfCurrency: Int? = 0,

    @SerializedName("volume_1hrs_usd")
    val valueNegotiated1hrs: Double? = 0.0,

    @SerializedName("volume_1day_usd")
    val valueNegotiated1day: Double? = 0.0,

    @SerializedName("volume_1mth_usd")
    val valueNegotiated1mth: Double? = 0.0,

    @SerializedName("price_usd")
    val priceUsd: Double? = 0.0,

    val url: String? = "",

    @SerializedName("id_icon")
    val keyCoin: String? = ""
) : Serializable {

    fun getPathUrlImage(): String {
        return Constants.BASE_URL_IMG.plus(getCoinFileName())
    }

    private fun getCoinFileName(): String {
        return "${keyCoin?.replace("-", "")}.png"
    }
}
