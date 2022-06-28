package com.example.take_my_money.presenter.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.data.dao.CoinDataBase
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.data.dao.ICoinDAO
import com.example.take_my_money.data.repository.RepositoryDataSource
import com.example.take_my_money.databinding.ItemListCoinBinding
import com.example.take_my_money.presenter.interfaces.IOnclik
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoinAdapter(private val onclickCoin: IOnclik, private val context: Context) :
    ListAdapter<CoinEntity, CoinAdapter.CoinViewHolder>(DiffCallback()), IOnclik {

    override fun onClickCoins(coin: CoinEntity) {}

    class CoinViewHolder(val binding: ItemListCoinBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            binding = ItemListCoinBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val iCoinDAO: ICoinDAO = CoinDataBase.getInstance(context).iCoinDAO
        val dao = RepositoryDataSource(iCoinDAO)
        val item = getItem(position)

        holder.itemView.contentDescription =
            """Moeda digital $position${item.id_icon}, ${item.name}, ${item.price_usd}${item.volume_1day_usd} , ${item.volume_1hrs_usd}, ${item.volume_1mth_usd}"""

        holder.binding.txtCoin.text = item.name
        holder.binding.txtCoinModel.text = item.asset_id
        holder.itemView.setOnClickListener {
            onclickCoin.onClickCoins(item)
        }
        try {
            CoroutineScope(Dispatchers.Main).launch {
                if (dao.getByAssetId(item.asset_id.toString()) != null) {
                    holder.binding.imageStar.visibility = View.VISIBLE
                } else {
                    holder.binding.imageStar.visibility = View.INVISIBLE
                }
            }
        } catch (e: Exception) {
            e.message
        }
        try {
            holder.binding.txtPriceCoin.text = NumberFormat.getInstance().format(item.price_usd)
            if (item.id_icon != null) {
                Picasso.get().load(item.getPathUrlImage()).into(holder.binding.imagemCoin)
            }
        } catch (e: Exception) {
            holder.binding.imagemCoin
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CoinEntity>() {
        override fun areItemsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.asset_id == newItem.asset_id
        }
    }
}
