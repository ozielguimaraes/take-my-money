package com.example.take_my_money.data.repository

import com.example.take_my_money.data.api.IWebService
import com.example.take_my_money.data.utils.Constants

class RepositoryAllCoins(private val retrofit: IWebService) {

    fun getAllCoins() = getAllCoinsRepository()

    private fun getAllCoinsRepository() = retrofit.getAllCoins(Constants.API_KEY4)
}
