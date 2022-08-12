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
    private val dataSourceAbstract: IDataSourceAbstract
) : ViewModel() {

    private val _returnDataBase = MutableLiveData<CoinEntity?>()
    val returnDataBase: LiveData<CoinEntity?> get() = _returnDataBase

    fun insertCoinDataBase(getAssetIdCoin: CoinEntity) = viewModelScope.launch {
        getAssetIdCoin.let { dataSourceAbstract.insertCoinI(it) }
    }

    fun deleteCoinDataBase(getAssetIdCoin: String) = viewModelScope.launch {
        dataSourceAbstract.deleteCoin(getAssetIdCoin)
    }

    fun loadDataBase() = viewModelScope.launch {
        dataSourceAbstract.getAllCoins()
    }

    fun getByAssetId(assetId: String) = CoroutineScope(Dispatchers.IO).launch {
        val returnCoinDataBase = dataSourceAbstract.getByAssetId(assetId)
        _returnDataBase.postValue(returnCoinDataBase)
    }

    fun castCoinToCoinEntity(coinDetails: Coin): CoinEntity {
        return CoinEntity(
            id = coinDetails.id,
            currencyAbbreviation = coinDetails.currencyAbbreviation,
            nameCurrency = coinDetails.nameCurrency,
            typeOfCurrency = coinDetails.typeOfCurrency,
            valueNegotiated1day = coinDetails.valueNegotiated1day,
            valueNegotiated1hrs = coinDetails.valueNegotiated1hrs,
            valueNegotiated1mth = coinDetails.valueNegotiated1mth,
            priceUsd = coinDetails.priceUsd,
            url = coinDetails.url,
            keyCoin = coinDetails.keyCoin
        )
    }
}
