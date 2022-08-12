package com.example.take_my_money.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ICoinDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coin: CoinEntity): Long

    @Query("SELECT * FROM coin")
    suspend fun allCoin(): List<CoinEntity>

    @Query("SELECT * FROM coin WHERE currencyAbbreviation = :assetId")
    suspend fun getByAssetId(assetId: String): CoinEntity?

    @Query("DELETE FROM coin WHERE nameCurrency = :id")
    suspend fun delete(id: String)
}
