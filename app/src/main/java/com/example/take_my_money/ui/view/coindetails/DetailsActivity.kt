package com.example.take_my_money.ui.view.coindetails

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.R
import com.example.take_my_money.databinding.DetailsActivityBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.utils.Constants
import com.example.take_my_money.ui.view.coinlist.CoinListActivity
import com.example.take_my_money.ui.view.coinsfavorite.FavoriteActivity
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: DetailsActivityBinding
    private lateinit var viewModel: CoinDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DetailsActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBottom()

        val coinIDAO: ICoinDAO =
            CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            CoinDetailsViewModelFactory(RepositoryDataSource(coinIDAO))
        )[CoinDetailsViewModel::class.java]

        viewModel.loadDataBase()
        getCoinListScreen()
    }

    private fun getCoinListScreen() {
        val coinDetails = intent.getSerializableExtra(Constants.KEY_INTENT)
        passDataToScreen(coinDetails as CoinEntity?)
    }

    private fun passDataToScreen(coinDetails: CoinEntity?) {
        coinDetails?.let {
            binding.txCoin.text = coinDetails.name
            binding.txValue.text = NumberFormat.getInstance().format(coinDetails.price_usd)
            binding.txValueHour.text =
                NumberFormat.getInstance().format(coinDetails.volume_1hrs_usd)
            binding.txValueDay.text = NumberFormat.getInstance().format(coinDetails.volume_1day_usd)
            binding.txValueMonth.text =
                NumberFormat.getInstance().format(coinDetails.volume_1mth_usd)
            Picasso.get().load(coinDetails.getPathUrlImage()).into(binding.imView)
            checkCoinDataBase(coinDetails)
        }
    }

    private fun checkCoinDataBase(coin: CoinEntity) {
        viewModel.getByAssetId(coin.asset_id.toString())
        observers(coin)
    }

    private fun observers(coin: CoinEntity) {
        viewModel.returnDataBase.observe(this) {
            insertOrDeleteDataBase(coin)
        }
    }

    private fun insertOrDeleteDataBase(coin: CoinEntity) {
        if (viewModel.returnDataBase.value == null) {
            binding.btnAddRemove.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertCoinDataBase(coin)
                }
                Toast.makeText(this, getString(R.string.add_coin_database), Toast.LENGTH_LONG)
                    .show()
                screenFavorite()
            }
        } else {
            binding.btnAddRemove.text = getString(R.string.remover)
            binding.btnAddRemove.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    coin.name?.let { it1 -> viewModel.deleteCoinDataBase(it1) }
                }
                Toast.makeText(this, getString(R.string.remove_coin_database), Toast.LENGTH_LONG)
                    .show()
                screenFavorite()
            }
        }
    }

    private fun screenFavorite() {
        val intent = Intent(this, FavoriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupNavigationBottom() {
        binding.btnNavigationDet.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.ic_favorites -> {
                    startActivity((Intent(this, FavoriteActivity::class.java)))
                    finish()
                }
                R.id.ic_coins -> {
                    startActivity(Intent(this, CoinListActivity::class.java))
                    finish()
                }
            }
        }
    }
}
