package com.example.take_my_money.domain.abstracts

import com.example.take_my_money.domain.entities.Coin

interface ICoinAllAbstract {
    suspend fun getAllCoinsRepository(): List<Coin>?
}
