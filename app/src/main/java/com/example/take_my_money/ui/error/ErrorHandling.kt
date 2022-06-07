package com.example.take_my_money.ui.error

import com.example.take_my_money.ui.data.entity.CoinEntity

sealed class ErrorHandling<out T : Any> {

    object Loading : ErrorHandling<Nothing>()
    data class Success<out T : Any>(val listCoin: List<CoinEntity>?) : ErrorHandling<T>()
    data class ErrorLimitsRequest(val throwable: String) : ErrorHandling<Nothing>()
    data class ErrorUnauthorized(val throwable: String) : ErrorHandling<Nothing>()
    data class ErrorBadRequest(val throwable: String) : ErrorHandling<Nothing>()
    data class ErrorForbidden(val throwable: String) : ErrorHandling<Nothing>()
    data class ErrorNoData(val throwable: String) : ErrorHandling<Nothing>()
}
