package com.example.take_my_money.presentation.interfaces

import com.example.take_my_money.domain.entities.Coin

interface IOnClickCoinList {
    fun onClickCoins(coin: Coin)
}
