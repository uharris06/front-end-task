package com.uharris.frontendtask.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uharris.frontendtask.R
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.databinding.ItemOfferBinding

class OffersAdapter(val listener: OfferListener): ListAdapter<Offer, OffersAdapter.OfferViewHolder>(DiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallBack : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer) =
            oldItem.id == newItem.id && oldItem.isInBasket == newItem.isInBasket
    }

    inner class OfferViewHolder(private val binding: ItemOfferBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            with(binding) {
                productTextView.text = offer.title
                priceTextView.text = root.context.getString(R.string.price, offer.price/100)
                if (offer.image.isNotEmpty()) {
                    Picasso.get().load(offer.image).into(productImageView)
                }
                addRemoveButton.apply {
                    text = if (offer.isInBasket)
                        root.context.getString(R.string.remove_basket)
                    else root.context.getString(R.string.add_basket)
                    setOnClickListener {
                        if (offer.isInBasket)
                            listener.removeOffer(offer)
                        else
                            listener.addOffer(offer)
                    }
                }
            }
        }
    }

    interface OfferListener {
        fun addOffer(offer: Offer)

        fun removeOffer(offer: Offer)
    }
}