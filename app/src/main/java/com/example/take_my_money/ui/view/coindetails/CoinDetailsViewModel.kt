package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
    private val iRepositoryDataSource: IRepositoryDataSource
) : ViewModel() {

    private val _returnDataBase = MutableLiveData<CoinEntity?>()
    val returnDataBase: LiveData<CoinEntity?> get() = _returnDataBase

    suspend fun insertCoinDataBase(getAssetIdCoin: CoinEntity) {
        getAssetIdCoin.let { iRepositoryDataSource.insertCoinI(it) }
    }

    suspend fun deleteCoinDataBase(getAssetIdCoin: String) {
        iRepositoryDataSource.deleteCoin(getAssetIdCoin)
    }

    fun loadDataBase() {
        viewModelScope.launch {
            iRepositoryDataSource.getAllCoins()
        }
    }

    fun getByAssetId(assetId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val returnCoinDataBase = iRepositoryDataSource.getByAssetId(assetId)
            _returnDataBase.postValue(returnCoinDataBase)
        }
    }
}
