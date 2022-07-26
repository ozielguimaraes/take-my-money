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
            coin.asset_id,
            coin.name,
            coin.type_is_crypto,
            coin.volume_1hrs_usd,
            coin.volume_1day_usd,
            coin.volume_1mth_usd,
            coin.price_usd,
            coin.url,
            coin.id_icon
        )
}
