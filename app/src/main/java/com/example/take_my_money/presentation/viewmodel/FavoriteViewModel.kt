package com.example.take_my_money.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.IDataSourceAbstract
import com.example.take_my_money.domain.entities.Coin
import kotlinx.coroutines.launch

class FavoriteViewModel(private val iDataSourceAbstract: IDataSourceAbstract) : ViewModel() {

    private val _loadDataBase = MutableLiveData<List<CoinEntity>>()
    val loadDataBase: LiveData<List<CoinEntity>> get() = _loadDataBase

    fun loadDataBase() {
        try {
            viewModelScope.launch {
                _loadDataBase.postValue(iDataSourceAbstract.getAllCoins())
            }
        } catch (e: Exception) {
            Log.i("TAG", "loadDataBase: ${e.cause}")
        }
    }

    fun castCoinEntityToCoin(coinFavorite: CoinEntity): Coin {
        return Coin(
            id = coinFavorite.id,
            currencyAbbreviation = coinFavorite.asset_id,
            nameCurrency = coinFavorite.name,
            typeOfCurrency = coinFavorite.type_is_crypto,
            valueNegotiated1hrs = coinFavorite.volume_1hrs_usd,
            valueNegotiated1mth = coinFavorite.volume_1mth_usd,
            valueNegotiated1day = coinFavorite.volume_1day_usd,
            priceUsd = coinFavorite.price_usd,
            url = coinFavorite.url,
            keyCoin = coinFavorite.id_icon
        )
    }
}
