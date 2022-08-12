package com.example.take_my_money.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.take_my_money.R
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.databinding.ActivityDetailsCoinBinding
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.presentation.utils.Constants
import com.example.take_my_money.presentation.viewmodel.CoinDetailsViewModel
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsCoinBinding
    private val viewModel: CoinDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBottom()

        loadDataBase()
        getCoinListScreen()
        buttonBack()
    }

    private fun loadDataBase() {
        viewModel.loadDataBase()
    }

    private fun getCoinListScreen() {
        val coinDetails = intent.getSerializableExtra(Constants.KEY_INTENT)
        val coinFavorite = intent.getSerializableExtra(Constants.KEY_INTENT)
        if (coinDetails == null) {
            passDataToScreen(coinDetails)
        } else {
            passDataToScreen(coinFavorite as Coin?)
        }
    }

    private fun passDataToScreen(coinDetails: Coin?) {
        coinDetails?.let {
            if (coinDetails.keyCoin != null) {
                Picasso.get().load(coinDetails.getPathUrlImage()).into(binding.imView)
            } else {
                Picasso.get().load(R.drawable.im_coin).into(binding.imView)
            }
            try {
                binding.txCoin.text = coinDetails.currencyAbbreviation
                binding.txValue.text = NumberFormat.getInstance().format(coinDetails.priceUsd)
                binding.txValueHour.text = NumberFormat.getInstance().format(coinDetails.valueNegotiated1hrs)
                binding.txValueDay.text = NumberFormat.getInstance().format(coinDetails.valueNegotiated1day)
                binding.txValueMonth.text = NumberFormat.getInstance().format(coinDetails.valueNegotiated1mth)
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } finally {
                checkCoinDataBase(viewModel.castCoinToCoinEntity(coinDetails))
            }
        }
    }

    private fun checkCoinDataBase(coin: CoinEntity) {
        viewModel.getByAssetId(coin.currencyAbbreviation.toString())
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
                    coin.nameCurrency?.let { it1 -> viewModel.deleteCoinDataBase(it1) }
                }
                Toast.makeText(this, getString(R.string.remove_coin_database), Toast.LENGTH_LONG).show()
                callingScreenFavorite()
            }
        }
    }

    private fun addStarFavoriteOrNot() {
        if (viewModel.returnDataBase.value == null) {
            binding.imageViewStar.visibility = View.INVISIBLE
        } else {
            binding.imageViewStar.visibility = View.VISIBLE
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
