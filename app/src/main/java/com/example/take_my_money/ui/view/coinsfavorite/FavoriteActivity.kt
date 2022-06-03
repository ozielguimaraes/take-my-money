package com.example.take_my_money.ui.view.coinsfavorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.databinding.FavoriteActivityBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.utils.Constants
import com.example.take_my_money.ui.view.adapter.Onclik
import com.example.take_my_money.ui.view.coindetails.DetailsActivity
import com.example.take_my_money.ui.view.coinlist.CoinListActivity
import java.text.SimpleDateFormat
import java.util.*

class FavoriteActivity : AppCompatActivity(), Onclik {

    private lateinit var binding: FavoriteActivityBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FavoriteActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBottom()

        val coinIDAO: ICoinDAO = CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(
                RepositoryDataSource(coinIDAO)
            )
        )[FavoriteViewModel::class.java]
        viewModel.loadDataBase()
        setupView()
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
            adapter.submitList(coinsDataBase)
            binding.recyclerFavorite.adapter = adapter
        }
    }

    override fun onClickCoins(coin: CoinEntity) {
        callNewScreen(coin)
    }

    private fun callNewScreen(coin: CoinEntity) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
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
}
