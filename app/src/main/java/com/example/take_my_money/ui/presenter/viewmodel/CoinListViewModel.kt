package com.example.take_my_money.ui.presenter.viewmodel

import androidx.lifecycle.*
import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.repository.IRepositoryDataSource
import com.example.take_my_money.ui.data.repository.RepositoryAllCoins
import com.example.take_my_money.ui.data.repository.RepositoryDataSource
import com.example.take_my_money.ui.domain.ResultWrapper
import com.example.take_my_money.ui.domain.exceptions.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CoinListViewModel(
    private val repository: RepositoryAllCoins,
    private val iRepositoryDataBase: IRepositoryDataSource
) : ViewModel() {

    private val _listcoins = MutableLiveData<ResultWrapper<List<CoinEntity>>>()
    val listcoins: LiveData<ResultWrapper<List<CoinEntity>>> get() = _listcoins

    private val _listCoinsAdapter = MutableLiveData<List<CoinEntity>>()
    val listCoinsAdapter: LiveData<List<CoinEntity>> get() = _listCoinsAdapter

    private val _errorMsg = MutableLiveData<ResultWrapper<String>>()
    val errorMsg: LiveData<ResultWrapper<String>> get() = _errorMsg

    fun requestCoinApi() {
        val requestApi: Call<List<CoinEntity>> = repository.getAllCoins()
        requestApi.enqueue(object : retrofit2.Callback<List<CoinEntity>> {
            override fun onResponse(
                call: Call<List<CoinEntity>>,
                response: Response<List<CoinEntity>>
            ) {
                _listcoins.value = ResultWrapper.Loading
                try {
                    when {
                        response.isSuccessful -> {
                            val resultCoinApi = response.body()?.filter { it.type_is_crypto == 1 }
                            _listcoins.value = ResultWrapper.Success(resultCoinApi)
                            _listCoinsAdapter.value = resultCoinApi?.let { resultCoinApi }
                        }
                        response.code() == 400 -> {
                            _errorMsg.value = ResultWrapper.Error(BadRequestException(), response.code())
                        }
                        response.code() == 401 -> {
                            _errorMsg.value = ResultWrapper.Error(UnauthorizedException(), response.code())
                        }
                        response.code() == 403 -> {
                            _errorMsg.value = ResultWrapper.Error(ForbiddenException(), response.code())
                        }
                        response.code() == 429 -> {
                            _errorMsg.value = ResultWrapper.Error(LimitsRequestException(), response.code())
                        }
                        response.code() == 550 -> {
                            _errorMsg.value = ResultWrapper.Error(NoDataException(), response.code())
                        }
                    }
                } catch (e: Exception) {
                    e.fillInStackTrace().message
                }
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
                _errorMsg.value = ResultWrapper.Error(UseInternetException())
            }
        })
    }

    fun loadDataBase() {
        viewModelScope.launch {
            iRepositoryDataBase.getAllCoins()
        }
    }

    class CoinListViewModelFactory(
        private val repository: RepositoryAllCoins,
        private val iRepository: RepositoryDataSource
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
                CoinListViewModel(this.repository, this.iRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel not foun")
            }
        }
    }
}
