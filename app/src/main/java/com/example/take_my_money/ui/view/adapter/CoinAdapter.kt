package com.example.take_my_money.ui.view

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.databinding.CoinsListItemBinding
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.view.adapter.Onclik
import com.squareup.picasso.Picasso
import java.text.NumberFormat

class CoinAdapter(private val onclikcoin: Onclik) :
    ListAdapter<CoinEntity, CoinAdapter.CoinViewHolder>(DiffCallback()), Onclik {

    override fun onClickCoins(movie: CoinEntity) {
    }

    class CoinViewHolder(val binding: CoinsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            binding = CoinsListItemBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtCoin.text = item.name
        holder.binding.txtCoinModel.text = item.asset_id

        try {
            Picasso.get().load(item.getPathUrlImage()).into(holder.binding.imagemCoin)
            holder.binding.txtPriceCoin.text = NumberFormat.getInstance().format(item.price_usd)
        } catch (e: Exception) {
            holder.binding.imagemCoin
        }

        holder.itemView.setOnClickListener {
            onclikcoin.onClickCoins(item)
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
