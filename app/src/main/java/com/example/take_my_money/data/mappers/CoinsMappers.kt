package com.example.take_my_money.data.mappers

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.entities.Coin

object CoinsMappers {
    fun fromRemoteToDomain(coinList: List<CoinEntity>): List<Coin> {
        return coinList.map { fromRemoteToDomain(it) }
    }

    fun fromRemoteToDomain(coin: CoinEntity): Coin =
        Coin(
            coin.id,
            coin.currencyAbbreviation,
            coin.nameCurrency,
            coin.typeOfCurrency,
            coin.valueNegotiated1hrs,
            coin.valueNegotiated1day,
            coin.valueNegotiated1mth,
            coin.priceUsd,
            coin.url,
            coin.keyCoin
        )
}
