package com.example.take_my_money.ui.view.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import retrofit2.Call
import retrofit2.Response

class CoinListViewModel(
    private val repository: RepositoryAllCoins,
    private val iRepositoryDataBase: IRepositoryDataSource
) : ViewModel() {
    private val _listcoins = MutableLiveData<List<CoinEntity>?>()
    val listcoins: LiveData<List<CoinEntity>?> get() = _listcoins

    private val _errorMsg = MutableLiveData<String>()
    val erroMsg: LiveData<String> get() = _errorMsg

    fun requestCoinApi() {
        val requestApi: Call<List<CoinEntity>> = repository.getAllCoins()
        requestApi.enqueue(object : retrofit2.Callback<List<CoinEntity>> {
            override fun onResponse(
                call: Call<List<CoinEntity>>,
                response: Response<List<CoinEntity>>
            ) {

                val listresult = response.body()
                _listcoins.postValue(listresult?.filter { it.type_is_crypto == 1 })
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
                _errorMsg.postValue(t.message)
            }
        })
    }
}
