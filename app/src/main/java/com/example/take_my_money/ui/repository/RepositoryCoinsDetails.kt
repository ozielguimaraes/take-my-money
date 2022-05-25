package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.utils.Constants

class RepositoryCoinsDetails(private val retrofit: IWebService) {

    fun getCoinsDetails(asset_id: String?) {
        retrofit.getCoinsDetails(asset_id, Constants.API_KEY)
    }

    fun getImageCoin() {
        retrofit.getImageCoins(Constants.API_KEY)
    }
}