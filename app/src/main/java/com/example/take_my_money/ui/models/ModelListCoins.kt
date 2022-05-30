package com.example.take_my_money.ui.models

import com.example.take_my_money.ui.utils.Constants
import java.io.Serializable

data class ModelListCoins(
    val asset_id: String?,
    val name: String?,
    val volume_1hrs_usd: Double?,
    val volume_1day_usd: Double?,
    val volume_1mth_usd: Double?,
    val price_usd: Double?,
    val url: String?,
    val id_icon: String
) : Serializable {

    fun getPathUrlImage(): String {
        return Constants.BASE_URL_IMG.plus(getCoinFileName())
    }

    fun getCoinFileName(): String {
        return "${id_icon.replace("-", "")}.png"
    }
}
