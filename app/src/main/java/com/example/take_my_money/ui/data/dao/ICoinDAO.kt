package com.example.take_my_money.ui.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.take_my_money.ui.data.entity.CoinEntity

@Dao
interface ICoinDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(coin: CoinEntity): Long

    @Query("SELECT * FROM coin")
    suspend fun allCoin(): Array<CoinEntity>

    @Query("DELETE FROM coin WHERE name = :id")
    suspend fun delete(id: String)
}
