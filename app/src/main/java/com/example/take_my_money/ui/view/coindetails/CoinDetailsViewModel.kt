package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinDetailsViewModel(
    private val iRepositoryDataSource: IRepositoryDataSource,
    private val repositoryCoinsDetails: RepositoryCoinsDetails
) : ViewModel() {

    private val _coinDetails = MutableLiveData<CoinEntity>()
    val coinDetail: LiveData<CoinEntity> get() = _coinDetails

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> get() = _messageError

    fun getDetailsApiCoin(assetId: String) {
        val call: Call<List<CoinEntity>> = repositoryCoinsDetails.getCoinsDetails(assetId = assetId)
        call.enqueue(object : Callback<List<CoinEntity>> {
            override fun onResponse(call: Call<List<CoinEntity>>, response: Response<List<CoinEntity>>) {
                val result = response.body()
                result?.let {
                    if (result.isNotEmpty()) {
                        _coinDetails.postValue(result[0])
                    } else {
                        _messageError.postValue(response.message())
                    }
                }
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
                _messageError.postValue(t.message)
            }
        })
    }

    suspend fun insertCoinDetailsDataBase() {
        CoroutineScope(Dispatchers.Main).launch {
            _coinDetails.value?.let { iRepositoryDataSource.insertCoinI(it) }
        }
    }

    suspend fun deleteCoin() {
        _coinDetails.value.let {
            it?.let { it1 -> iRepositoryDataSource.deleteCoin(it1.name) }
        }
    }

    suspend fun allCoins(coinDetails: CoinEntity): Array<CoinEntity> {
        return iRepositoryDataSource.getAllCoins()
    }
}
