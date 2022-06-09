package com.example.take_my_money.ui.domain

import com.example.take_my_money.ui.data.dao.CoinEntity

sealed class ResultWrapper<out T : Any> {

    object Loading : ResultWrapper<Nothing>()
    data class Success<out T : Any>(val listCoin: List<CoinEntity>?) : ResultWrapper<T>()
    data class Error(val exception: Exception, val code: Int = 0) : ResultWrapper<Nothing>()
}
