package com.example.take_my_money.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.IDataSourceAbstract
import com.example.take_my_money.domain.entities.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val iDataSourceAbstract: IDataSourceAbstract
) : ViewModel() {

    private val _returnDataBase = MutableLiveData<CoinEntity?>()
    val returnDataBase: LiveData<CoinEntity?> get() = _returnDataBase

    suspend fun insertCoinDataBase(getAssetIdCoin: CoinEntity) {
        getAssetIdCoin.let { iDataSourceAbstract.insertCoinI(it) }
    }

    suspend fun deleteCoinDataBase(getAssetIdCoin: String) {
        iDataSourceAbstract.deleteCoin(getAssetIdCoin)
    }

    fun loadDataBase() {
        viewModelScope.launch {
            iDataSourceAbstract.getAllCoins()
        }
    }

    fun getByAssetId(assetId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val returnCoinDataBase = iDataSourceAbstract.getByAssetId(assetId)
            _returnDataBase.postValue(returnCoinDataBase)
        }
    }

    fun castCoinToCoinEntity(coinDetails: Coin): CoinEntity {
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
