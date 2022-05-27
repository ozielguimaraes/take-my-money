package com.example.take_my_money.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.databinding.DetailsActivityBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.view.coindetails.CoinDetailsViewModel
import com.example.take_my_money.ui.view.coindetails.CoinDetailsViewModelFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: DetailsActivityBinding
    private lateinit var viewModel: CoinDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DetailsActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val subscriberDAOI: ICoinDAO =
            CoinDataBase.getInstance(this).ICoinDAO
        val repositoryCoinsDetails = RepositoryCoinsDetails(IWebService.retrofit)
        viewModel = ViewModelProvider(
            this,
            CoinDetailsViewModelFactory(RepositoryDataSource(subscriberDAOI), repositoryCoinsDetails)
        )[CoinDetailsViewModel::class.java]
    }
}
