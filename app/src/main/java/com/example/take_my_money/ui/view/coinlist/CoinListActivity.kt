package com.example.take_my_money.ui.view.coinlist

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.error.ErrorHandling
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.utils.Constants
import com.example.take_my_money.ui.view.CoinAdapter
import com.example.take_my_money.ui.view.adapter.Onclik
import com.example.take_my_money.ui.view.coindetails.DetailsActivity
import com.example.take_my_money.ui.view.coinsfavorite.FavoriteActivity
import java.text.SimpleDateFormat
import java.util.*

class CoinListActivity : AppCompatActivity(), Onclik {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CoinListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val coinIDAO: ICoinDAO =
            CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            CoinListViewModelFactory(
                RepositoryAllCoins(retrofit = IWebService.getBaseUrl()),
                RepositoryDataSource(coinIDAO)
            )
        )[CoinListViewModel::class.java]
        setupNavigationBottom()
        setupObservers()
        viewModel.loadDataBase()
        viewModel.requestCoinApi()
        setupView()
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
        viewModel.errorMsg.observe(this) {
            Toast.makeText(this, getString(R.string.error_check_internet).plus(it.toString()), Toast.LENGTH_LONG).show()
        }
        val adapter = CoinAdapter(this)
        viewModel.listcoins.observe(this) { resultCoinApi ->
            when (resultCoinApi) {
                is ErrorHandling.Success -> {
                    adapter.submitList(resultCoinApi.listCoin)
                    binding.RecyclerviewCoins.adapter = adapter
                }
                else -> {
                    errorHandling(resultCoinApi)
                }
            }
        }
    }

    private fun errorHandling(resultCoinApi: ErrorHandling<List<CoinEntity>>) {
        when (resultCoinApi) {
            is ErrorHandling.Loading -> {
                // ProgressBar ou Lista mocada talvez...
            }
            is ErrorHandling.ErrorLimitsRequest -> {
                Toast.makeText(this, R.string.too_many_requests, Toast.LENGTH_LONG).show()
            }
            is ErrorHandling.ErrorBadRequest -> {
                Toast.makeText(this, R.string.bad_request, Toast.LENGTH_LONG).show()
            }
            is ErrorHandling.ErrorForbidden -> {
                Toast.makeText(this, R.string.forbidden, Toast.LENGTH_LONG).show()
            }
            is ErrorHandling.ErrorNoData -> {
                Toast.makeText(this, R.string.no_data, Toast.LENGTH_LONG).show()
            }
            is ErrorHandling.ErrorUnauthorized -> {
                Toast.makeText(this, R.string.unauthorized, Toast.LENGTH_LONG).show()
            }
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
        val adapter = CoinAdapter(this@CoinListActivity)
        if (query.isNullOrEmpty()) {
            adapter.submitList(viewModel.coinNullOrExist.value)
        } else {
            adapter.submitList(
                viewModel.coinNullOrExist.value?.filter {
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
