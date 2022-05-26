package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.utils.Constants

class RepositoryCoinsDetails(private val retrofit: IWebService) {

    fun getCoinsDetails(assetId: String?) = retrofit.getCoinsDetails(assetId, Constants.API_KEY)

    fun getImageCoin() {
        retrofit.getImageCoins(Constants.API_KEY)
    }
}
