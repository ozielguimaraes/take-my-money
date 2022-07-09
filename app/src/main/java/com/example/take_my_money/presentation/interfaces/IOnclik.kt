package com.example.take_my_money.presentation.interfaces

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.entities.CoinDomainEntities

interface IOnclik {
    fun onClickCoins(coin: CoinDomainEntities)
}
