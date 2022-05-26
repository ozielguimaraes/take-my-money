package com.example.take_my_money.ui.models

import java.io.Serializable

data class ModelListCoins(
    val asset_id: String?,
    val name: String?,
    val volume_1hrs_usd: String?,
    val volume_1day_usd: String?,
    val volume_1mth_usd: String?,
    val price_usd: String?,
    val url: String?
) : Serializable
