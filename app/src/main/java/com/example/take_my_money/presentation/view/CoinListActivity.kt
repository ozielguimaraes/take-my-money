package com.example.take_my_money.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.databinding.ActivityCoinListBinding
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.domain.exceptions.ResultWrapper
import com.example.take_my_money.presentation.adapters.CoinAdapter
import com.example.take_my_money.presentation.interfaces.IOnClickCoinList
import com.example.take_my_money.presentation.utils.Constants
import com.example.take_my_money.presentation.viewmodel.CoinListViewModel
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListActivity : AppCompatActivity(), IOnClickCoinList {

    private lateinit var binding: ActivityCoinListBinding
    private val viewModel: CoinListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadDataBase()
        setupNavigationBottom()
        requestApi()
        setupObservers()
        setupView()
    }

    private fun loadDataBase() {
        viewModel.loadDataBase()
    }

    private fun setupNavigationBottom() {
        binding.btnNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_favorites -> {
                    startActivity((Intent(this, FavoriteActivity::class.java)))
                    finish()
                }
            }
        }
    }

    private fun requestApi() {
        viewModel.requestApiListCoin()
    }

    private fun setupObservers() {
        viewModel.errorMsg.observe(this) { resultError ->
            setupError(resultError)
        }
        viewModel.listCoinsLiveData.observe(this) {
            setupAdapter(it)
        }

        viewModel.listCoinsResultWrapper.observe(this) {
            loadCoinList(it)
        }
    }

    private fun loadCoinList(resultCoinApi: ResultWrapper<List<Coin>>) {
        when (resultCoinApi) {
            is ResultWrapper.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ResultWrapper.Success -> {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupAdapter(list: List<Coin>?) {
        val adapter = CoinAdapter(this, this)
        binding.RecyclerviewCoins.layoutManager = LinearLayoutManager(this)
        binding.RecyclerviewCoins.setHasFixedSize(true)
        adapter.submitList(list)
        binding.RecyclerviewCoins.adapter = adapter
    }

    private fun setupError(resultError: ResultWrapper<String>) {
        when (resultError) {
            is ResultWrapper.Error -> {
                Toast.makeText(this, resultError.exception.message, Toast.LENGTH_LONG).show()
                screenTryAgain()
            }
        }
    }

    private fun screenTryAgain() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.textTryAgain.visibility = View.VISIBLE
        binding.btnTryAgain.visibility = View.VISIBLE
        binding.imageError.visibility = View.VISIBLE
        binding.btnTryAgain.setOnClickListener {
            requestApi()
            binding.progressBar.visibility = View.VISIBLE
            binding.textTryAgain.visibility = View.INVISIBLE
            binding.btnTryAgain.visibility = View.INVISIBLE
            binding.imageError.visibility = View.INVISIBLE
        }
    }

    private fun setupView() {
        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)

        binding.RecyclerviewCoins.layoutManager = LinearLayoutManager(this)
        binding.RecyclerviewCoins.setHasFixedSize(true)

        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return filterCoins(query)
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return filterCoins(query)
            }
        })
    }

    private fun filterCoins(query: String?): Boolean {
        val adapter = CoinAdapter(this@CoinListActivity, this)
        if (query.isNullOrEmpty()) {
            adapter.submitList(viewModel.listCoinsLiveData.value)
        } else {
            adapter.submitList(
                viewModel.listCoinsLiveData.value?.filter {
                    it.nameCurrency?.contains(query) ?: false
                }
            )
        }
        binding.RecyclerviewCoins.adapter = adapter
        return true
    }

    override fun onClickCoins(coin: Coin) {
        callingScreenDetailsCoin(coin)
    }

    private fun callingScreenDetailsCoin(coin: Coin) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
    }
}
