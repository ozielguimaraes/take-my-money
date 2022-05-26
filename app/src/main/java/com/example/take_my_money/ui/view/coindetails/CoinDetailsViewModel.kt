package com.example.take_my_money.ui.view.coindetails

import androidx.lifecycle.ViewModel
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails

class CoinDetailsViewModel(
    private val iRepositoryDataSource: IRepositoryDataSource,
    private val repositoryCoinsDetails: RepositoryCoinsDetails
) : ViewModel()
