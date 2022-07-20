package com.example.take_my_money.data.api

import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.presentation.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface IWebService {

    @GET(Constants.PATH_URL_ALL_COINS)
    suspend fun getAllCoins(
        @Query("apikey") apikey: String?
    ): Response<List<CoinEntity>>

    companion object {
        private val retrofit: IWebService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(IWebService::class.java)
        }

        fun getBaseUrl(): IWebService {
            return retrofit
        }
    }
}
