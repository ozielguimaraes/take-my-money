package com.example.take_my_money.domain.exceptions

sealed class ResultWrapper<out T : Any> {

    object Loading : ResultWrapper<Nothing>()
    data class Success<out T : Any>(val listCoin: T?) : ResultWrapper<T>()
    data class Error(val exception: Exception, val code: Int = 0) : ResultWrapper<Nothing>()
}
