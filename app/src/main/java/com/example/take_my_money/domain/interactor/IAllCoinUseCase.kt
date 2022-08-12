package com.example.take_my_money.domain.interactor

import com.example.take_my_money.domain.entities.Coin

interface IAllCoinUseCase {
    suspend fun getListCoins(): List<Coin>?
}
