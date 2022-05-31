package com.example.take_my_money.ui.view.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import java.lang.IllegalArgumentException

class CoinListViewModelFactory(
    private val repository: RepositoryAllCoins
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
            CoinListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not foun")
        }
    }
}
