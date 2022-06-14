package com.example.take_my_money.ui.presenter.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.domain.usecase.UseCaseDataSource
import com.example.take_my_money.ui.data.repository.RepositoryDataSource
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCaseDataSource: UseCaseDataSource) : ViewModel() {

    private val _loadDataBase = MutableLiveData<List<CoinEntity>>()
    val loadDataBase: LiveData<List<CoinEntity>> get() = _loadDataBase

    fun loadDataBase() {
        viewModelScope.launch {
            _loadDataBase.postValue(useCaseDataSource.getAllCoins())
        }
    }

    class FavoriteViewModelFactory(
        private val repositoryDataSource: RepositoryDataSource
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                FavoriteViewModel(this.repositoryDataSource) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}
