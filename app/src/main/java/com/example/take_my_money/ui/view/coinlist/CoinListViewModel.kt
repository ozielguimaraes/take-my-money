package com.example.take_my_money.ui.view.coinlist
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.models.ModelListCoins
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import retrofit2.Call
import retrofit2.Response

class CoinListViewModel(
    private val repository: RepositoryAllCoins
) : ViewModel() {
    private val _listcoins = MutableLiveData<List<ModelListCoins>?>()
    val listcoins: MutableLiveData<List<ModelListCoins>?> get() = _listcoins

    private val _errorMsg = MutableLiveData<String>()

    fun getAllCoins() {
        val requestApi: Call<List<ModelListCoins>> = repository.getAllCoins()
        requestApi.enqueue(object : retrofit2.Callback<List<ModelListCoins>> {
            override fun onResponse(
                call: Call<List<ModelListCoins>>,
                response: Response<List<ModelListCoins>>
            ) {

                val listresult = response.body()
                _listcoins.postValue(listresult?.filter { it.type_is_crypto == 1 })
            }

            override fun onFailure(call: Call<List<ModelListCoins>>, t: Throwable) {
                _errorMsg.postValue(t.message)
            }
        })
    }
}
