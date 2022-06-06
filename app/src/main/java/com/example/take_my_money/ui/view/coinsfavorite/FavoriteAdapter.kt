package com.example.take_my_money.ui.view.coinsfavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.databinding.ItemFavoriteRecyclerBinding
import com.example.take_my_money.ui.data.entity.CoinEntity
import com.example.take_my_money.ui.view.adapter.Onclik

class FavoriteAdapter(private val onclick: Onclik) :
    ListAdapter<CoinEntity, FavoriteAdapter.MyViewHolderFavorite>(DiffCallbackFavorite()), Onclik {

    override fun onClickCoins(coin: CoinEntity) {
    }

    class MyViewHolderFavorite(val binding: ItemFavoriteRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFavorite {
        return MyViewHolderFavorite(
            ItemFavoriteRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolderFavorite, position: Int) {
        val itemCoin = getItem(position)

        holder.binding.textCoin.text = itemCoin.name
        holder.binding.textCoinAsset.text = itemCoin.asset_id
        holder.binding.textValueCoin.text = itemCoin.price_usd.toString()

        holder.itemView.setOnClickListener { onclick.onClickCoins(itemCoin) }
    }

    class DiffCallbackFavorite : DiffUtil.ItemCallback<CoinEntity>() {
        override fun areItemsTheSame(oldItem: CoinEntity, newItem: CoinEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinEntity, newItem: CoinEntity): Boolean {
            return oldItem.asset_id == newItem.asset_id
        }
    }
}
