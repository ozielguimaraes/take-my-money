package com.example.take_my_money.presentation.interfaces

import com.example.take_my_money.data.dao.CoinEntity

interface IOnClickFavorite {
    fun onClickFavorite(coinFavorite: CoinEntity)
}
