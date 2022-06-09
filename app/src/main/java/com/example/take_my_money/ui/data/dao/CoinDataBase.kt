package com.example.take_my_money.ui.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1)
abstract class CoinDataBase : RoomDatabase() {

    abstract val iCoinDAO: ICoinDAO

    companion object {
        @Volatile
        private var INSTANCE: CoinDataBase? = null

        fun getInstance(context: Context): CoinDataBase {
            synchronized(this) {
                var instance: CoinDataBase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CoinDataBase::class.java,
                        "CryptoCurrencies"
                    ).build()
                }
                return instance
            }
        }
    }
}
