package com.example.take_my_money.presentation.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import com.example.take_my_money.data.repository.RepositoryDataSource
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCaseDataSource: UseCaseDataSource) : ViewModel() {

    private val _loadDataBase = MutableLiveData<List<CoinEntity>>()
    val loadDataBase: LiveData<List<CoinEntity>> get() = _loadDataBase

    fun loadDataBase() {
        viewModelScope.launch {
            _loadDataBase.postValue(useCaseDataSource.getAllCoins())
        }
    }
}
