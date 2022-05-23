package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.utils.Constants

class RepositoryAllCoins(private val retrofit: IWebService) {

    fun getAllCoins() {
        retrofit.getAllCoins(Constants.API_KEY)
    }
}
