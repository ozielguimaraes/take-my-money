package com.example.take_my_money.presentation.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import com.example.take_my_money.data.repository.RepositoryDataSource
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
}
