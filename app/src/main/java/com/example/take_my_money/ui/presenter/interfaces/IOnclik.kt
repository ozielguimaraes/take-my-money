package com.example.take_my_money.ui.presenter.interfaces

import com.example.take_my_money.ui.data.dao.CoinEntity

interface IOnclik {
    fun onClickCoins(coin: CoinEntity)
}
