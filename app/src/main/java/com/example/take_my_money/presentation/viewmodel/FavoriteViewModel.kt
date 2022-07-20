package com.example.take_my_money.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import com.example.take_my_money.domain.entities.CoinDomainEntities
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCaseDataSource: UseCaseDataSource) : ViewModel() {

    private val _loadDataBase = MutableLiveData<List<CoinEntity>>()
    val loadDataBase: LiveData<List<CoinEntity>> get() = _loadDataBase

    fun loadDataBase() {
        try {
            viewModelScope.launch {
                _loadDataBase.postValue(useCaseDataSource.getAllCoins())
            }
        } catch (e: Exception) {
            Log.i("TAG", "loadDataBase: ${e.cause}")
        }
    }

    fun castListCoinFavorite(coinFavorite: CoinEntity): CoinDomainEntities {
        return CoinDomainEntities(
            id = coinFavorite.id,
            asset_id = coinFavorite.asset_id,
            name = coinFavorite.name,
            type_is_crypto = coinFavorite.type_is_crypto,
            volume_1hrs_usd = coinFavorite.volume_1hrs_usd,
            volume_1mth_usd = coinFavorite.volume_1mth_usd,
            volume_1day_usd = coinFavorite.volume_1day_usd,
            price_usd = coinFavorite.price_usd,
            url = coinFavorite.url,
            id_icon = coinFavorite.id_icon
        )
    }
}
