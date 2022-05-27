package com.example.take_my_money.ui.repository

import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.entity.CoinEntity

class RepositoryDataSource(private val ICoinDAO: ICoinDAO) : IRepositoryDataSource {

    override suspend fun insertCoinI(
        coin: CoinEntity
    ): Long {
        return ICoinDAO.insert(coin)
    }

    override suspend fun deleteCoin(id: Long) {
        return ICoinDAO.delete(id)
    }
}
