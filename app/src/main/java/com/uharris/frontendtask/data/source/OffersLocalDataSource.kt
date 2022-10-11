package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.dto.Offer

interface OffersLocalDataSource {

    fun saveOfferBasket(offer: Offer)

    fun deleteOfferBasket(offer: Offer)

    fun getOffersFromBasket(): List<Offer>
}