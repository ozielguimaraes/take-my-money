package com.example.take_my_money.ui.interfaces

import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.models.ModelListCoins
import com.example.take_my_money.ui.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IWebService {

    @GET(Constants.PATH_URL_ALL_COINS)
    fun getAllCoins(
        @Query("apikey") apikey: String?
    ): Call<List<ModelListCoins>>

    @GET(Constants.PATH_URL_DETAILS)
    fun getCoinsDetails(
        @Path("coin") coin: String?,
        @Query("apikey") apikey: String?
    ): Call<List<CoinEntity>>

    companion object {

        val retrofit: IWebService by lazy {
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
