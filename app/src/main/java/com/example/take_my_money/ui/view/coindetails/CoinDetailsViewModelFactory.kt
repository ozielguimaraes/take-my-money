package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails
import com.example.take_my_money.ui.repository.RepositoryDataSource

class CoinDetailsViewModelFactory(
    private val repositoryDataSource: RepositoryDataSource,
    private val repositoryCoinsDetails: RepositoryCoinsDetails
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CoinDetailsViewModel::class.java)) {
            CoinDetailsViewModel(this.repositoryDataSource, this.repositoryCoinsDetails) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}
