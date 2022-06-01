package com.example.take_my_money.ui.view
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_my_money.Onclik
import com.example.take_my_money.databinding.ActivityMainBinding
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.models.ModelListCoins
import com.example.take_my_money.ui.repository.RepositoryAllCoins
import com.example.take_my_money.ui.utils.Constants
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

        viewModel = ViewModelProvider(
            this, CoinListViewModelFactory(RepositoryAllCoins(retrofit = IWebService.getBaseUrl()))
        )[CoinListViewModel::class.java]
        setupObservers()
        viewModel.getAllCoins()
        setupView()
    }

    private fun setupView() {
        val date = Calendar.getInstance().time
        val dateTimeFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.textViewDateNow.text = dateTimeFormat.format(date)
        binding.RecyclerviewCoins.layoutManager = LinearLayoutManager(this)
        binding.RecyclerviewCoins.setHasFixedSize(true)
        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return filterCoins(query)
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return filterCoins(query)
            }
        })
    }

    private fun setupObservers() {
        viewModel.listcoins.observe(this) { listCoin ->
            val adapter = CoinAdapter(this)
            adapter.submitList(listCoin)
            binding.RecyclerviewCoins.adapter = adapter
        }
    }

    private fun filterCoins(query: String?): Boolean {
        val adapter = CoinAdapter(this@CoinListActivity)
        if (query.isNullOrEmpty()) {
            adapter.submitList(viewModel.listcoins.value)
        }
        else {
            adapter.submitList(viewModel.listcoins.value?.filter {
                it.name?.contains(query) ?: false
            })
        }
        binding.RecyclerviewCoins.adapter = adapter
        return true
    }

    override fun onClikCoins(coins: ModelListCoins) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.KEY_INTENT, coins.asset_id)
        startActivity(intent)
    }

}
