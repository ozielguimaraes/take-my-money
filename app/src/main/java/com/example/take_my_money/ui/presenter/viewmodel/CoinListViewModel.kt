package com.example.take_my_money.ui.presenter.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.repository.RepositoryDataSource
import com.example.take_my_money.ui.domain.exceptions.*
import com.example.take_my_money.ui.domain.usecase.UseCaseAllCoin
import com.example.take_my_money.ui.domain.usecase.UseCaseDataSource
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CoinListViewModel(
    private val useCaseAllCoin: UseCaseAllCoin,
    private val useCaseDataBase: UseCaseDataSource
) : ViewModel() {

    private val _listcoins = MutableLiveData<ResultWrapper<List<CoinEntity>>>()
    val listcoins: LiveData<ResultWrapper<List<CoinEntity>>> get() = _listcoins

    private val _listCoinsAdapter = MutableLiveData<List<CoinEntity>>()
    val listCoinsAdapter: LiveData<List<CoinEntity>> get() = _listCoinsAdapter

    private val _errorMsg = MutableLiveData<ResultWrapper<String>>()
    val errorMsg: LiveData<ResultWrapper<String>> get() = _errorMsg

    fun requestApi() {
        try {
            viewModelScope.launch {
                _listcoins.postValue(ResultWrapper.Success(useCaseAllCoin.getListCoin()))
                _listCoinsAdapter.postValue(useCaseAllCoin.getListCoin())
            }
        } catch (http: HttpException) {
            when {
                http.code() == 400 -> {
                    _errorMsg.value = ResultWrapper.Error(BadRequestException(), http.code())
                }
                http.code() == 401 -> {
                    _errorMsg.value = ResultWrapper.Error(UnauthorizedException(), http.code())
                }
                http.code() == 403 -> {
                    _errorMsg.value = ResultWrapper.Error(ForbiddenException(), http.code())
                }
                http.code() == 429 -> {
                    _errorMsg.value = ResultWrapper.Error(LimitsRequestException(), http.code())
                }
                http.code() == 550 -> {
                    _errorMsg.value = ResultWrapper.Error(NoDataException(), http.code())
                }
            }
        }
    }

    fun loadDataBase() {
        viewModelScope.launch {
            useCaseDataBase.getAllCoins()
        }
    }

    class CoinListViewModelFactory(
        private val useCaseAllCoin: UseCaseAllCoin,
        private val iRepository: RepositoryDataSource
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
                CoinListViewModel(this.useCaseAllCoin, this.iRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel not foun")
            }
        }
    }
}
