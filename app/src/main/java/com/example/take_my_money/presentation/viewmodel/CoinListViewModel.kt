package com.example.take_my_money.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.domain.abstracts.UseCaseDataSource
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.domain.exceptions.*
import com.example.take_my_money.domain.usecases.UseCaseAllCoin
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CoinListViewModel(
    private val useCaseAllCoin: UseCaseAllCoin,
    private val useCaseDataBase: UseCaseDataSource
) : ViewModel() {

    private val _listCoinsResultWrapper = MutableLiveData<ResultWrapper<List<Coin>>>()
    val listCoinsResultWrapper: LiveData<ResultWrapper<List<Coin>>> get() = _listCoinsResultWrapper

    private val _listCoinsMutableLiveData = MutableLiveData<List<Coin>>()
    val listCoinsLiveData: LiveData<List<Coin>> get() = _listCoinsMutableLiveData

    private val _errorMsg = MutableLiveData<ResultWrapper<String>>()
    val errorMsg: LiveData<ResultWrapper<String>> get() = _errorMsg

    fun requestApiListCoin() {
        _listCoinsResultWrapper.value = ResultWrapper.Loading
        try {
            viewModelScope.launch {
                _listCoinsResultWrapper.postValue(
                    useCaseAllCoin.getListCoin()?.let { ResultWrapper.Success(it) }
                )
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
}
