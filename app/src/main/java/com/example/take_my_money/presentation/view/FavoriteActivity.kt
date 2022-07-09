package com.example.take_my_money.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.databinding.ActivityFavoriteCoinBinding
import com.example.take_my_money.domain.entities.CoinDomainEntities
import com.example.take_my_money.presentation.adapters.FavoriteAdapter
import com.example.take_my_money.presentation.interfaces.IOnclik
import com.example.take_my_money.presentation.utils.Constants
import com.example.take_my_money.presentation.viewmodel.FavoriteViewModel
import java.text.SimpleDateFormat
import java.util.*

class FavoriteActivity : AppCompatActivity(), IOnclik {

    private lateinit var binding: ActivityFavoriteCoinBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val coinIDAO: ICoinDAO = CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                RepositoryDataSource(coinIDAO)
            )
        )[FavoriteViewModel::class.java]

        loadDataBase()
        setupNavigationBottom()
        setupView()
    }

    private fun loadDataBase() {
        viewModel.loadDataBase()
    }

    private fun setupNavigationBottom() {
        binding.btnNavigationFav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_coins -> {
                    startActivity(Intent(this, CoinListActivity::class.java))
                    finish()
                }
                R.id.ic_favorites -> {
                    startActivity((Intent(this, FavoriteActivity::class.java)))
                }
            }
        }
    }

    private fun setupView() {
        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)
        binding.recyclerFavorite.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerFavorite.setHasFixedSize(true)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loadDataBase.observe(this) { coinsDataBase ->
            val adapter = FavoriteAdapter(this)
            /* adapter.submitList(coinsDataBase)*/
            binding.recyclerFavorite.adapter = adapter
        }
    }

    override fun onClickCoins(coin: CoinDomainEntities) {
        callNewScreen(coin)
    }

    private fun callNewScreen(coin: CoinDomainEntities) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
    }
}
