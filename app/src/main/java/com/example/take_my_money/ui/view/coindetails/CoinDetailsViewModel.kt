package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinDetailsViewModel(
    private val iRepositoryDataSource: IRepositoryDataSource,
    private val repositoryCoinsDetails: RepositoryCoinsDetails
) : ViewModel() {

    private val _coinDetails = MutableLiveData<List<CoinEntity>?>()
    val coinDetail: LiveData<List<CoinEntity>?> get() = _coinDetails

    private val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> get() = _messageError

    fun getDetailsCoin(assetId: String) {
        val call: Call<List<CoinEntity>> = repositoryCoinsDetails.getCoinsDetails(assetId = assetId)
        call.enqueue(object : Callback<List<CoinEntity>> {
            override fun onResponse(call: Call<List<CoinEntity>>, response: Response<List<CoinEntity>>) {
                val result = response.body()
                if (result != null) {
                    _coinDetails.postValue(result)
                }
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
                _messageError.postValue(t.message)
            }
        })
    }

    suspend fun insertCoinDetailsDataBase(resultCoinDetails: CoinEntity) {
        try {
            val id = iRepositoryDataSource.insertCoinI(resultCoinDetails)
            if (id > 0)
                iRepositoryDataSource.insertCoinI(resultCoinDetails)
        } catch (e: java.lang.Exception) {
            _messageError.postValue(e.message)
        }
    }

    suspend fun deleteCoinDataBase(id: Long) {
        try {
            if (id == id) {
                iRepositoryDataSource.deleteCoin(id)
            }
        } catch (e: Exception) {
            _messageError.postValue(e.message)
        }
    }
}
