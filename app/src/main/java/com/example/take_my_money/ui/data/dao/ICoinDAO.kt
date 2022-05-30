package com.example.take_my_money.ui.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.take_my_money.ui.data.entity.CoinEntity

@Dao
interface ICoinDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinEntity): Long

    @Query("DELETE FROM coin WHERE id = :id")
    suspend fun delete(id: Long)
}
