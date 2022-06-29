package com.example.take_my_money.presentation.interfaces

import com.example.take_my_money.data.dao.CoinEntity

interface IOnclik {
    fun onClickCoins(coin: CoinEntity)
}
