package com.example.take_my_money.ui.view.coinsfavorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.ui.repository.RepositoryDataSource

class FavoriteViewModelFactory(
    private val repositoryDataSource: RepositoryDataSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(this.repositoryDataSource) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}
