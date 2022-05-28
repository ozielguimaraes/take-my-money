package com.example.take_my_money.ui.repository

import android.util.Log
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.entity.CoinEntity

class RepositoryDataSource(private val ICoinDAO: ICoinDAO) : IRepositoryDataSource {

    override suspend fun insertCoinI(
        coin: List<CoinEntity>
    ): List<Long> {
        Log.i("TAG", "insertCoinI: $coin")
        return ICoinDAO.insert(coin)
    }

    override suspend fun update(coin: List<CoinEntity>) {
        return ICoinDAO.update(coin)
    }

    override suspend fun deleteCoin(id: Long) {
        return ICoinDAO.delete(id)
    }

    override suspend fun getAllCoins(): List<CoinEntity> {
        return ICoinDAO.allCoin()
    }
}
