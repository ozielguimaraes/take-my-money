package com.example.take_my_money.ui.view.coinsfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import kotlinx.coroutines.launch

class FavoriteViewModel(private val iRepositoryDataSource: IRepositoryDataSource) : ViewModel() {

    private val _loadDataBase = MutableLiveData<List<CoinEntity>>()
    val loadDataBase: LiveData<List<CoinEntity>> get() = _loadDataBase

    fun loadDataBase() {
        viewModelScope.launch {
            _loadDataBase.postValue(iRepositoryDataSource.getAllCoins())
        }
    }
}
