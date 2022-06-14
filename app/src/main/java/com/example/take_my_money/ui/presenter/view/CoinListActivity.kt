package com.example.take_my_money.ui.presenter.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.databinding.ActivityCoinListBinding
import com.example.take_my_money.ui.data.api.IWebService
import com.example.take_my_money.ui.data.dao.CoinDataBase
import com.example.take_my_money.ui.data.dao.CoinEntity
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.repository.RepositoryAllCoins
import com.example.take_my_money.ui.data.repository.RepositoryDataSource
import com.example.take_my_money.ui.data.utils.Constants
import com.example.take_my_money.ui.domain.exceptions.ResultWrapper
import com.example.take_my_money.ui.domain.UseCase.UseCaseAllCoin
import com.example.take_my_money.ui.presenter.adapter.CoinAdapter
import com.example.take_my_money.ui.presenter.interfaces.IOnclik
import com.example.take_my_money.ui.presenter.viewmodel.CoinListViewModel
import java.text.SimpleDateFormat
import java.util.*

class CoinListActivity : AppCompatActivity(), IOnclik {

    private lateinit var binding: ActivityCoinListBinding
    private lateinit var viewModel: CoinListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val coinIDAO: ICoinDAO =
            CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            CoinListViewModel.CoinListViewModelFactory(
                UseCaseAllCoin(RepositoryAllCoins(IWebService.getBaseUrl())),
                RepositoryDataSource(coinIDAO)
            )
        )[CoinListViewModel::class.java]

        loadDataBase()
        setupNavigationBottom()
        setupObservers()
        requestApi()
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

    private fun setupObservers() {
        viewModel.errorMsg.observe(this) { resultError ->
            setupError(resultError)
        }
        viewModel.listcoins.observe(this) { resultCoinApi ->
            loadCoinList(resultCoinApi)
        }
        viewModel.listCoinsAdapter.observe(this) {
        }
    }

    private fun loadCoinList(resultCoinApi: ResultWrapper<List<CoinEntity>>) {
        val adapter = CoinAdapter(this, this)
        when (resultCoinApi) {
            is ResultWrapper.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ResultWrapper.Success -> {
                binding.progressBar.visibility = View.INVISIBLE
                adapter.submitList(resultCoinApi.listCoin)
                binding.RecyclerviewCoins.adapter = adapter
            }
        }
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

    private fun requestApi() {
        viewModel.requestApi()
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
            adapter.submitList(viewModel.listCoinsAdapter.value)
        } else {
            adapter.submitList(
                viewModel.listCoinsAdapter.value?.filter {
                    it.name?.contains(query) ?: false
                }
            )
        }
        binding.RecyclerviewCoins.adapter = adapter
        return true
    }

    override fun onClickCoins(coin: CoinEntity) {
        callingScreenDetailsCoin(coin)
    }

    private fun callingScreenDetailsCoin(coin: CoinEntity) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
    }
}
