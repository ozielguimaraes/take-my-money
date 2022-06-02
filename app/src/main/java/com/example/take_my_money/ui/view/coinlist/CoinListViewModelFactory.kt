package com.example.take_my_money.ui.view.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import com.example.take_my_money.ui.repository.RepositoryDataSource

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
