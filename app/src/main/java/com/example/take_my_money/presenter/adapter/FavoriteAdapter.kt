package com.example.take_my_money.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.databinding.ItemFavoriteRecyclerBinding
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.presenter.interfaces.IOnclik
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val onclick: IOnclik) :
    ListAdapter<CoinEntity, FavoriteAdapter.MyViewHolderFavorite>(DiffCallbackFavorite()), IOnclik {

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

        try {
            if (itemCoin.id_icon != null) {
                Picasso.get().load(itemCoin.getPathUrlImage()).into(holder.binding.imageCoinFavorite4)
            }
        } catch (e: Exception) {
            holder.binding.imageCoinFavorite4
        }

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
