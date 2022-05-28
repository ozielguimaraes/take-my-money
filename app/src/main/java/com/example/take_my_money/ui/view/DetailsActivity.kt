package com.example.take_my_money.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.take_my_money.databinding.DetailsActivityBinding
import com.example.take_my_money.ui.data.dao.ICoinDAO
import com.example.take_my_money.ui.data.database.CoinDataBase
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.interfaces.IWebService
import com.example.take_my_money.ui.repository.RepositoryCoinsDetails
import com.example.take_my_money.ui.repository.RepositoryDataSource
import com.example.take_my_money.ui.view.coindetails.CoinDetailsViewModel
import com.example.take_my_money.ui.view.coindetails.CoinDetailsViewModelFactory
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

        val coinIDAO: ICoinDAO =
            CoinDataBase.getInstance(this).iCoinDAO
        val repositoryCoinsDetails = RepositoryCoinsDetails(IWebService.retrofit)
        viewModel = ViewModelProvider(
            this,
            CoinDetailsViewModelFactory(RepositoryDataSource(coinIDAO), repositoryCoinsDetails)
        )[CoinDetailsViewModel::class.java]

        teste()
    }

    private fun teste() {
        viewModel.getDetailsCoin("BTC")
        viewModel.coinDetail.observe(this) { listCoin ->
            listCoin?.let {
                binding.txCoin.text = listCoin[0].asset_id
                binding.txValue.text = NumberFormat.getInstance().format(listCoin[0].price_usd)
                binding.txValueHour.text = NumberFormat.getInstance().format(listCoin[0].volume_1hrs_usd)
                binding.txValueDay.text = NumberFormat.getInstance().format(listCoin[0].volume_1day_usd)
                binding.txValueMonth.text = NumberFormat.getInstance().format(listCoin[0].volume_1mth_usd)
                Picasso.get().load(listCoin[0].getPathUrlImage()).into(binding.imView)
                insertFavorites(listCoin)
            }
        }
        viewModel.messageError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun insertFavorites(listCoin: List<CoinEntity>) {
        binding.btnAddRemove.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.insertCoinDetailsDataBase(listCoin)
            }
        }
    }
}
