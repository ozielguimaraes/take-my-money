package com.example.take_my_money.domain.usecases

import com.example.take_my_money.domain.abstracts.ICoinAllAbstract
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.domain.interactor.IAllCoinUseCase

class AllCoinUseCase(private val iCoinAllAbstract: ICoinAllAbstract): IAllCoinUseCase {

    suspend fun getListCoin(): List<Coin>? = getListCoins()

    override suspend fun getListCoins(): List<Coin>? {
        val resultCoin = iCoinAllAbstract.getAllCoinsRepository()
        resultCoin?.let {
            return resultCoin.filter { it.type_is_crypto == 1 }
        }
        return null
    }
}
