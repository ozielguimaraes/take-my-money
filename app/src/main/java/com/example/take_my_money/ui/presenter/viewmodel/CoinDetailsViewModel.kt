package com.example.take_my_money.ui.presenter.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.repository.IRepositoryDataSource
import com.example.take_my_money.ui.data.repository.RepositoryDataSource
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

    class CoinDetailsViewModelFactory(
        private val repositoryDataSource: RepositoryDataSource,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CoinDetailsViewModel::class.java)) {
                CoinDetailsViewModel(this.repositoryDataSource) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}
