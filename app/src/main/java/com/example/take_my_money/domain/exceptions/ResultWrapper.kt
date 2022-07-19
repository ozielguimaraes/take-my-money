package com.example.take_my_money.domain.exceptions

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.entities.CoinDomainEntities

sealed class ResultWrapper<out T : Any> {

    object Loading : ResultWrapper<Nothing>()
    class Success<out T : Any>(val listCoin: List<CoinDomainEntities>?) : ResultWrapper<T>()
    class Error(val exception: Exception, val code: Int = 0) : ResultWrapper<Nothing>()
}
