package com.example.take_my_money.domain.abstracts

import com.example.take_my_money.domain.entities.Coin

interface ICoinAllRepository {

    suspend fun getAllCoinsRepository(): List<Coin>?
}
