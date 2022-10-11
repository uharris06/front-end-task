package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.utils.AppResult

interface OffersRemoteDataSource {

    suspend fun getOffers(): AppResult<List<Offer>>
}