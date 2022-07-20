package com.example.take_my_money.domain.usecases

import com.example.take_my_money.domain.abstracts.ICoinAllRepository
import com.example.take_my_money.domain.entities.CoinDomainEntities

class UseCaseAllCoin(private val iCoinAllRepository: ICoinAllRepository) {

    suspend fun getListCoin(): List<CoinDomainEntities>? {
        return getListCoins()
    }

    private suspend fun getListCoins(): List<CoinDomainEntities>? {
        val resultCoin = iCoinAllRepository.getAllCoinsRepository()
        resultCoin?.let {
            return resultCoin.filter { it.type_is_crypto == 1 }
        }
        return null
    }
}
