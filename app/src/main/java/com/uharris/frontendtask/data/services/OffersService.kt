package com.uharris.frontendtask.data.services

import com.uharris.frontendtask.data.dto.OfferResponse
import retrofit2.http.GET

interface OffersService {
    @GET(OFFER_URL)
    suspend fun getOffers(): OfferResponse

    companion object {
        private const val OFFER_URL = "offers"
    }
}