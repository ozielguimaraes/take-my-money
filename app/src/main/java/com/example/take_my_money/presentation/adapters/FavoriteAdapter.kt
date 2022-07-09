package com.example.take_my_money.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.take_my_money.databinding.ItemFavoriteRecyclerBinding
import com.example.take_my_money.domain.entities.CoinDomainEntities
import com.example.take_my_money.presentation.interfaces.IOnclik
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val onclick: IOnclik) :
    ListAdapter<CoinDomainEntities, FavoriteAdapter.MyViewHolderFavorite>(DiffCallbackFavorite()),
    IOnclik {

    override fun onClickCoins(coin: CoinDomainEntities) {
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
            """Moeda Digital ${position}${itemCoin.name}, ${itemCoin.asset_id}, ${itemCoin.price_usd}"""

        holder.binding.textCoin.text = itemCoin.name
        holder.binding.textCoinAsset.text = itemCoin.asset_id
        holder.binding.textValueCoin.text = itemCoin.price_usd.toString()

        try {
            if (itemCoin.id_icon != null) {
                Picasso.get().load(itemCoin.getPathUrlImage())
                    .into(holder.binding.imageCoinFavorite4)
            }
        } catch (e: Exception) {
            holder.binding.imageCoinFavorite4
        }
        holder.itemView.setOnClickListener { onclick.onClickCoins(itemCoin) }
    }

    class DiffCallbackFavorite : DiffUtil.ItemCallback<CoinDomainEntities>() {
        override fun areItemsTheSame(
            oldItem: CoinDomainEntities,
            newItem: CoinDomainEntities
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CoinDomainEntities,
            newItem: CoinDomainEntities
        ): Boolean {
            return oldItem.asset_id == newItem.asset_id
        }
    }
}
