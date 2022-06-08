package com.example.take_my_money.ui.error

import com.example.take_my_money.ui.data.entity.CoinEntity

sealed class ErrorHandling<out T : Any> {

    object Loading : ErrorHandling<Nothing>()
    data class Success<out T : Any>(val listCoin: List<CoinEntity>?) : ErrorHandling<T>()
    data class Error(val exception: Exception, val code: Int = 0) : ErrorHandling<Nothing>()
}
