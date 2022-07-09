package com.example.take_my_money.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.R
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.databinding.ActivityDetailsCoinBinding
import com.example.take_my_money.presentation.utils.Constants
import com.example.take_my_money.presentation.viewmodel.CoinDetailsViewModel
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsCoinBinding
    private lateinit var viewModel: CoinDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBottom()

        val coinIDAO: ICoinDAO =
            CoinDataBase.getInstance(this).iCoinDAO
        viewModel = ViewModelProvider(
            this,
            CoinDetailsViewModel.CoinDetailsViewModelFactory(RepositoryDataSource(coinIDAO))
        )[CoinDetailsViewModel::class.java]

        loadDataBase()
        getCoinListScreen()
        buttonBack()
    }

    private fun loadDataBase() {
        viewModel.loadDataBase()
    }

    private fun addStarFavoriteOrNot() {
        if (viewModel.returnDataBase.value == null) {
            binding.imageViewStar.visibility = View.INVISIBLE
        } else {
            binding.imageViewStar.visibility = View.VISIBLE
        }
    }

    private fun getCoinListScreen() {
        val coinDetails = intent.getSerializableExtra(Constants.KEY_INTENT)
        val coinFavorite = intent.getSerializableExtra(Constants.KEY_INTENT)
        if (coinDetails == null) {
            passDataToScreen(coinDetails as CoinEntity?)
        } else {
            passDataToScreen(coinFavorite as CoinEntity?)
        }
    }

    private fun passDataToScreen(coinDetails: CoinEntity?) {
        coinDetails?.let {
            if (coinDetails.id_icon != null) {
              /*  Picasso.get().load(coinDetails.getPathUrlImage()).into(binding.imView)*/
            } else {
                Picasso.get().load(R.drawable.im_coin).into(binding.imView)
            }
            try {
                binding.txCoin.text = coinDetails.asset_id
                binding.txValue.text = NumberFormat.getInstance().format(coinDetails.price_usd)
                binding.txValueHour.text = NumberFormat.getInstance().format(coinDetails.volume_1hrs_usd)
                binding.txValueDay.text = NumberFormat.getInstance().format(coinDetails.volume_1day_usd)
                binding.txValueMonth.text = NumberFormat.getInstance().format(coinDetails.volume_1mth_usd)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } finally {
                checkCoinDataBase(coinDetails)
            }
        }
    }

    private fun checkCoinDataBase(coin: CoinEntity) {
        viewModel.getByAssetId(coin.asset_id.toString())
        observers(coin)
    }

    private fun observers(coin: CoinEntity) {
        viewModel.returnDataBase.observe(this) {
            insertOrDeleteDataBase(coin)
            addStarFavoriteOrNot()
        }
    }

    private fun insertOrDeleteDataBase(coin: CoinEntity) {
        if (viewModel.returnDataBase.value == null) {
            binding.btnAddRemove.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertCoinDataBase(coin)
                }
                Toast.makeText(this, getString(R.string.add_coin_database), Toast.LENGTH_LONG).show()
                callingScreenFavorite()
            }
        } else {
            binding.btnAddRemove.text = getString(R.string.remover)
            binding.btnAddRemove.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    coin.name?.let { it1 -> viewModel.deleteCoinDataBase(it1) }
                }
                Toast.makeText(this, getString(R.string.remove_coin_database), Toast.LENGTH_LONG).show()
                callingScreenFavorite()
            }
        }
    }

    private fun callingScreenFavorite() {
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

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            callingScreenListCoin()
        }
    }

    private fun callingScreenListCoin() {
        val intent = Intent(this, CoinListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
