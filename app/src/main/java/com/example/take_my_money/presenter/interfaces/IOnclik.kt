package com.example.take_my_money.presenter.interfaces

import com.example.take_my_money.data.dao.CoinEntity

interface IOnclik {
    fun onClickCoins(coin: CoinEntity)
}
