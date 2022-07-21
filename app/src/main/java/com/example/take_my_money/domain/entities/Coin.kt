package com.example.take_my_money.domain.entities

import com.example.take_my_money.presentation.utils.Constants
import java.io.Serializable

data class Coin(
    val id: Long,
    val asset_id: String?,
    val name: String?,
    val type_is_crypto: Int?,
    val volume_1hrs_usd: Double?,
    val volume_1day_usd: Double?,
    val volume_1mth_usd: Double?,
    val price_usd: Double?,
    val url: String?,
    val id_icon: String?
) : Serializable {

    fun getPathUrlImage(): String {
        return Constants.BASE_URL_IMG.plus(getCoinFileName())
    }

    private fun getCoinFileName(): String {
        return "${id_icon?.replace("-", "")}.png"
    }
}
