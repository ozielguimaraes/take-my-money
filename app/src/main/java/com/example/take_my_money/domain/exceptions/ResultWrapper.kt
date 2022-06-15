package com.example.take_my_money.domain.exceptions

import com.example.take_my_money.data.dao.CoinEntity

sealed class ResultWrapper<out T : Any> {

    object Loading : ResultWrapper<Nothing>()
    class Success<out T : Any>(val listCoin: List<CoinEntity>?) : ResultWrapper<T>()
    class Error(val exception: Exception, val code: Int = 0) : ResultWrapper<Nothing>()
}
