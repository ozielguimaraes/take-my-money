package com.example.take_my_money.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.R
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.databinding.ActivityCoinListBinding
import com.example.take_my_money.domain.entities.CoinDomainEntities
import com.example.take_my_money.domain.exceptions.ResultWrapper
import com.example.take_my_money.presentation.adapters.CoinAdapter
import com.example.take_my_money.presentation.interfaces.IOnclik
import com.example.take_my_money.presentation.utils.Constants
import com.example.take_my_money.presentation.viewmodel.CoinListViewModel
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListActivity : AppCompatActivity(), IOnclik {

    private lateinit var binding: ActivityCoinListBinding
    private val viewModel: CoinListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
        viewModel.listCoins.observe(this) { resultCoinApi ->
        }
        viewModel.listCoinsLiveData.observe(this) {
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
                /*adapter.submitList(resultCoinApi.listCoin)*/
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
        viewModel.requestApiListCoin()
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
                    it.name?.contains(query) ?: false
                }
            )
        }
        binding.RecyclerviewCoins.adapter = adapter
        return true
    }

    override fun onClickCoins(coin: CoinDomainEntities) {
        callingScreenDetailsCoin(coin)
    }

    private fun callingScreenDetailsCoin(coin: CoinDomainEntities) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coin)
        startActivity(intent)
    }
}
