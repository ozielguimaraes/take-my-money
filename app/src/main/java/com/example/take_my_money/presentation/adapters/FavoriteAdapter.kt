package com.example.take_my_money.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.data.dao.CoinEntity
import com.example.take_my_money.databinding.ItemFavoriteRecyclerBinding
import com.example.take_my_money.presentation.interfaces.IOnClickFavorite
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val onclick: IOnClickFavorite) :
    ListAdapter<CoinEntity, FavoriteAdapter.MyViewHolderFavorite>(DiffCallbackFavorite()),
    IOnClickFavorite {

    override fun onClickFavorite(coinFavorite: CoinEntity) {
        TODO()
    }

    class MyViewHolderFavorite(val binding: ItemFavoriteRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFavorite {
        return MyViewHolderFavorite(
            ItemFavoriteRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolderFavorite, position: Int) {
        val itemCoin = getItem(position)

        holder.itemView.contentDescription =
            """Moeda Digital ${position}${itemCoin.nameCurrency}, ${itemCoin.currencyAbbreviation}, ${itemCoin.priceUsd}"""

        holder.binding.textCoin.text = itemCoin.nameCurrency
        holder.binding.textCoinAsset.text = itemCoin.currencyAbbreviation
        holder.binding.textValueCoin.text = itemCoin.priceUsd.toString()

        try {
            if (itemCoin.keyCoin != null) {
                Picasso.get().load(itemCoin.getPathUrlImage())
                    .into(holder.binding.imageCoinFavorite4)
            }
        } catch (e: Exception) {
            holder.binding.imageCoinFavorite4
        }
        holder.itemView.setOnClickListener { onclick.onClickFavorite(itemCoin) }
    }

    class DiffCallbackFavorite : DiffUtil.ItemCallback<CoinEntity>() {
        override fun areItemsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.currencyAbbreviation == newItem.currencyAbbreviation
        }
    }
}
