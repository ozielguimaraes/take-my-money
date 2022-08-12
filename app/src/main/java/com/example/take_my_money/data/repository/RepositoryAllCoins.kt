package com.example.take_my_money.data.repository

import com.example.take_my_money.data.api.IWebService
import com.example.take_my_money.data.mappers.CoinsMappers
import com.example.take_my_money.domain.abstracts.ICoinAllAbstract
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.presentation.utils.Constants

class RepositoryAllCoins(private val retrofit: IWebService) : ICoinAllAbstract {

    override suspend fun getAllCoinsRepository(): List<Coin>? {
        val response = retrofit.getAllCoins(Constants.API_KEY)
        response.body()?.let {
            return CoinsMappers.fromRemoteToDomain(it)
        } ?: run {
            return null
        }
    }
}
