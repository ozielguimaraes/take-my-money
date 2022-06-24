package com.example.take_my_money.domain.data.dao

import com.example.take_my_money.data.dao.CoinEntity

class FakeListCointEntity {

    fun listAllCoins(): List<CoinEntity> {
        return listOf(
            CoinEntity(
                0,
                "BTCFAKE",
                "BitFake",
                1,
                999.99,
                9999.99,
                99999.99,
                9999999.99,
                null,
                null
            )
        )
    }
}
