package com.example.take_my_money.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import com.example.take_my_money.domain.entities.CoinDomainEntities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val useCaseDataSource: UseCaseDataSource
) : ViewModel() {

    private val _returnDataBase = MutableLiveData<CoinEntity?>()
    val returnDataBase: LiveData<CoinEntity?> get() = _returnDataBase

    suspend fun insertCoinDataBase(getAssetIdCoin: CoinEntity) {
        getAssetIdCoin.let { useCaseDataSource.insertCoinI(it) }
    }

    suspend fun deleteCoinDataBase(getAssetIdCoin: String) {
        useCaseDataSource.deleteCoin(getAssetIdCoin)
    }

    fun loadDataBase() {
        viewModelScope.launch {
            useCaseDataSource.getAllCoins()
        }
    }

    fun getByAssetId(assetId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val returnCoinDataBase = useCaseDataSource.getByAssetId(assetId)
            _returnDataBase.postValue(returnCoinDataBase)
        }
    }

    fun castListCoinDetails(coinDetails: CoinDomainEntities): CoinEntity {
        return CoinEntity(
            id = coinDetails.id,
            asset_id = coinDetails.asset_id,
            name = coinDetails.name,
            type_is_crypto = coinDetails.type_is_crypto,
            volume_1day_usd = coinDetails.volume_1day_usd,
            volume_1hrs_usd = coinDetails.volume_1hrs_usd,
            volume_1mth_usd = coinDetails.volume_1mth_usd,
            price_usd = coinDetails.price_usd,
            url = coinDetails.url,
            id_icon = coinDetails.id_icon
        )
    }
}
