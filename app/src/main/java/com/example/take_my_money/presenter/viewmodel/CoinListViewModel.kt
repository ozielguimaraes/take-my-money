package com.example.take_my_money.presenter.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.domain.exceptions.*
import com.example.take_my_money.domain.usecase.UseCaseAllCoin
import com.example.take_my_money.domain.usecase.UseCaseDataSource
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CoinListViewModel(
    private val useCaseAllCoin: UseCaseAllCoin,
    private val useCaseDataBase: UseCaseDataSource
) : ViewModel() {

    private val _listCoins = MutableLiveData<ResultWrapper<List<CoinEntity>>>()
    val listCoins: LiveData<ResultWrapper<List<CoinEntity>>> get() = _listCoins

    private val _listCoinsMutableLiveData = MutableLiveData<List<CoinEntity>>()
    val listCoinsLiveData: LiveData<List<CoinEntity>> get() = _listCoinsMutableLiveData

    private val _errorMsg = MutableLiveData<ResultWrapper<String>>()
    val errorMsg: LiveData<ResultWrapper<String>> get() = _errorMsg

    fun requestApiListCoin() {
        _listCoins.value = ResultWrapper.Loading
        try {
            viewModelScope.launch {
                _listCoins.postValue(ResultWrapper.Success(useCaseAllCoin.getListCoin()))
                _listCoinsMutableLiveData.postValue(useCaseAllCoin.getListCoin())
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
