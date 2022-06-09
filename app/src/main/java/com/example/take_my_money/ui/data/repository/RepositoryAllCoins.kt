package com.example.take_my_money.ui.data.repository

import com.example.take_my_money.ui.data.api.IWebService
import com.example.take_my_money.ui.data.utils.Constants

class RepositoryAllCoins(private val retrofit: IWebService) {

    fun getAllCoins() =
        retrofit.getAllCoins(Constants.API_KEY3)
}
