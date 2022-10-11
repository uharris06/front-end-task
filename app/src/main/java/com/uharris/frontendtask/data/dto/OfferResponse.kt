package com.uharris.frontendtask.data.dto

import com.uharris.frontendtask.utils.Mockable

@Mockable
data class OfferResponse(
    val items: List<Offer>
)