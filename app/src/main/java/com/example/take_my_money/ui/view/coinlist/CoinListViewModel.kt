package com.example.take_my_money.ui.view.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.error.ErrorHandling
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CoinListViewModel(
    private val repository: RepositoryAllCoins,
    private val iRepositoryDataBase: IRepositoryDataSource
) : ViewModel() {
    private val _listcoins = MutableLiveData<ErrorHandling<List<CoinEntity>>>()
    val listcoins: LiveData<ErrorHandling<List<CoinEntity>>> get() = _listcoins

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> get() = _errorMsg

    private val _coinNullOrExist = MutableLiveData<List<CoinEntity>>()
    val coinNullOrExist: LiveData<List<CoinEntity>> get() = _coinNullOrExist

    fun requestCoinApi() {
        val requestApi: Call<List<CoinEntity>> = repository.getAllCoins()
        requestApi.enqueue(object : retrofit2.Callback<List<CoinEntity>> {
            override fun onResponse(
                call: Call<List<CoinEntity>>,
                response: Response<List<CoinEntity>>
            ) {
                _listcoins.value = ErrorHandling.Loading
                try {
                    if (response.isSuccessful) {
                        val resultCoinApi = response.body()?.filter { it.type_is_crypto == 1 }
                        _listcoins.value = ErrorHandling.Success(resultCoinApi)
                    } else if (response.code() == 400) {
                        _listcoins.value = ErrorHandling.ErrorLimitsRequest(response.code().toString())
                    } else if (response.code() == 401) {
                        _listcoins.value = ErrorHandling.ErrorLimitsRequest(response.code().toString())
                    } else if (response.code() == 403) {
                        _listcoins.value = ErrorHandling.ErrorLimitsRequest(response.code().toString())
                    } else if (response.code() == 429) {
                        _listcoins.value = ErrorHandling.ErrorLimitsRequest(response.code().toString())
                    } else if (response.code() == 550) {
                        _listcoins.value = ErrorHandling.ErrorLimitsRequest(response.code().toString())
                    }
                } catch (e: Exception) {
                    e.fillInStackTrace().message
                }
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
                _errorMsg.postValue(t.fillInStackTrace().message)
            }
        })
    }

    fun loadDataBase() {
        viewModelScope.launch {
            _coinNullOrExist.postValue(iRepositoryDataBase.getAllCoins())
        }
    }
}
