package com.example.take_my_money.ui.view.coinlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.utils.Constants
import com.example.take_my_money.ui.view.CoinAdapter
import com.example.take_my_money.ui.view.adapter.Onclik
import com.example.take_my_money.ui.view.coindetails.DetailsActivity
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

        val coinIDAO: ICoinDAO = CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            CoinListViewModelFactory(
                RepositoryAllCoins(retrofit = IWebService.getBaseUrl()),
                RepositoryDataSource(coinIDAO)
            )
        )[CoinListViewModel::class.java]

        getAllCoins()
    }

    private fun getAllCoins() {
        binding.RecyclerviewCoins.layoutManager = LinearLayoutManager(this)
        binding.RecyclerviewCoins.setHasFixedSize(true)
        viewModel.listcoins.observe(this) { listCoin ->
            val adapter = CoinAdapter(this)
            adapter.submitList(listCoin)
            binding.RecyclerviewCoins.adapter = adapter
        }
    }

    override fun onClickCoins(coin: CoinEntity) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
    }
}
