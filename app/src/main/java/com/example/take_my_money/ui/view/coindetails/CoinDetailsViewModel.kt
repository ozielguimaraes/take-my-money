package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.models.ModelListCoins
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

    private val _coinDetails = MutableLiveData<List<CoinEntity>?>()
    val coinDetail: LiveData<List<CoinEntity>?> get() = _coinDetails

    fun getDetailsCoin(assetId: String) {
        val call: Call<List<CoinEntity>> = repositoryCoinsDetails.getCoinsDetails(assetId = assetId)
        call.enqueue(object : Callback<List<CoinEntity>> {
            override fun onResponse(call: Call<List<CoinEntity>>, response: Response<List<CoinEntity>>) {
                val result = response.body()
                if (result != null) {
                    _coinDetails.postValue(result)
                    CoroutineScope(Dispatchers.IO).launch {
                        // insertCoinDetailsDataBase(result[0])
                    }
                }
            }

            override fun onFailure(call: Call<List<CoinEntity>>, t: Throwable) {
            }
        })
    }

    suspend fun insertCoinDetailsDataBase(resultCoinDetails: CoinEntity) {
        iRepositoryDataSource.insertCoinI(resultCoinDetails)
    }
}
