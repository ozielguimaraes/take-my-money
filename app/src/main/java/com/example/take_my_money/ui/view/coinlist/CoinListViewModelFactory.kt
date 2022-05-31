package com.example.take_my_money.ui.view.coinlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryAllCoins

class CoinListViewModelFactory(
    private val repository: RepositoryAllCoins,
    private val iRepository: IRepositoryDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CoinListViewModel::class.java)) {
            CoinListViewModel(this.repository, this.iRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel not foun")
        }
    }
}
