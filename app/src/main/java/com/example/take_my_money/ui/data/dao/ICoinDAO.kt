package com.example.take_my_money.ui.data.dao

import androidx.room.*
import com.example.take_my_money.ui.data.entity.CoinEntity

@Dao
interface ICoinDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coin: List<CoinEntity>): List<Long>

    @Query("SELECT * FROM coin")
    suspend fun allCoin(): List<CoinEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(coin: List<CoinEntity>)

    @Query("DELETE FROM coin WHERE id = :id")
    suspend fun delete(id: Long)
}
