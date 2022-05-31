package com.example.take_my_money.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.models.ModelListCoins
import com.example.take_my_money.ui.repository.IRepositoryDataSource
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import com.example.take_my_money.ui.utils.Constants
import com.example.take_my_money.ui.view.adapter.Onclik
import com.example.take_my_money.ui.view.coinlist.CoinListViewModel
import com.example.take_my_money.ui.view.coinlist.CoinListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class CoinListActivity : AppCompatActivity(), Onclik {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CoinListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)

        viewModel = ViewModelProvider(
            this, CoinListViewModelFactory(RepositoryAllCoins(retrofit = IWebService.getBaseUrl()), )
        )[CoinListViewModel::class.java]
        getAllCoins()
    }

    private fun getAllCoins() {
        viewModel.getAllCoins()
        binding.RecyclerviewCoins.layoutManager = LinearLayoutManager(this)
        binding.RecyclerviewCoins.setHasFixedSize(true)
        viewModel.listcoins.observe(this) { listCoin ->
            val adapter = CoinAdapter(this)
            adapter.submitList(listCoin)
            binding.RecyclerviewCoins.adapter = adapter
        }
    }

    override fun onClikCoins(coins: ModelListCoins) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coins.asset_id)
        intent.putExtra(Constants.KEY_IS_FAVORITE_COIN, viewModel.isFavoriteCoin(coins.asset_id))
        startActivity(intent)
    }
}
