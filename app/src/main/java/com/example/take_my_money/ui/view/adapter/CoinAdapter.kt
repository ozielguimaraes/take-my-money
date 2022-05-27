package com.example.take_my_money.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.databinding.CoinsListItemBinding
import com.example.take_my_money.ui.models.ModelListCoins

class CoinAdapter : ListAdapter<ModelListCoins, CoinAdapter.CoinViewHolder>(DiffCallback()) {

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

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.binding.txtCoin.text = item.name
            holder.binding.txtCoinModel.text = item.asset_id
            holder.binding.txtPriceCoin.text = item.price_usd
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<ModelListCoins>() {
    override fun areItemsTheSame(oldItem: ModelListCoins, newItem: ModelListCoins): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ModelListCoins, newItem: ModelListCoins): Boolean {
        return oldItem.asset_id == newItem.asset_id
    }
}
