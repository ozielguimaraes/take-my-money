package com.example.take_my_money.presentation.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.databinding.ItemListCoinBinding
import com.example.take_my_money.domain.entities.Coin
import com.example.take_my_money.presentation.interfaces.IOnClickCoinList
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinAdapter(private val onclickCoin: IOnClickCoinList, private val context: Context) :
    ListAdapter<Coin, CoinAdapter.CoinViewHolder>(DiffCallback()), IOnClickCoinList {

    override fun onClickCoins(coin: Coin) {
        TODO()
    }

    class CoinViewHolder(val binding: ItemListCoinBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            binding = ItemListCoinBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val iCoinDAO: ICoinDAO = CoinDataBase.getInstance(context).iCoinDAO
        val dao = RepositoryDataSource(iCoinDAO)
        val item = getItem(position)

        holder.itemView.contentDescription =
            """Moeda digital $position${item.keyCoin},
                | ${item.nameCurrency},
                |  ${item.priceUsd}${item.valueNegotiated1day} ,
                |  ${item.valueNegotiated1hrs},
                |   ${item.valueNegotiated1mth}
            """.trimMargin()

        holder.binding.txtCoin.text = item.nameCurrency
        holder.binding.txtCoinModel.text = item.currencyAbbreviation
        holder.itemView.setOnClickListener {
            onclickCoin.onClickCoins(item)
        }
        try {
            CoroutineScope(Dispatchers.Main).launch {
                if (dao.getByAssetId(item.currencyAbbreviation.toString()) != null) {
                    holder.binding.imageStar.visibility = View.VISIBLE
                } else {
                    holder.binding.imageStar.visibility = View.INVISIBLE
                }
            }
        } catch (e: Exception) {
            Log.i("TAG", "onBindViewHolder: ${e.cause}")
        }
        try {
            holder.binding.txtPriceCoin.text = NumberFormat.getInstance().format(item.priceUsd)
            if (item.keyCoin != null) {
                Picasso.get().load(item.getPathUrlImage()).into(holder.binding.imagemCoin)
            }
        } catch (e: Exception) {
            holder.binding.imagemCoin
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Coin>() {

        override fun areItemsTheSame(
            oldItem: Coin,
            newItem: Coin
        ): Boolean {
            return oldItem.nameCurrency == newItem.nameCurrency
        }

        override fun areContentsTheSame(
            oldItem: Coin,
            newItem: Coin
        ): Boolean {
            return oldItem.currencyAbbreviation == newItem.currencyAbbreviation
        }
    }
}
