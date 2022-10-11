package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.preferences.OffersPreferences
import com.uharris.frontendtask.utils.Mockable
import javax.inject.Inject

@Mockable
class OffersLocalDataSourceImpl @Inject internal constructor(
    private val offersPreferences: OffersPreferences
): OffersLocalDataSource {
    override fun saveOfferBasket(offer: Offer) = offersPreferences.saveOffer(offer)

    override fun deleteOfferBasket(offer: Offer) = offersPreferences.deleteOffer(offer)

    override fun getOffersFromBasket() = offersPreferences.getOffers()
}