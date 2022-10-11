package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.services.OffersService
import com.uharris.frontendtask.utils.AppResult
import com.uharris.frontendtask.utils.Executor
import com.uharris.frontendtask.utils.Mockable
import javax.inject.Inject

@Mockable
class OffersRemoteDataSourceImpl @Inject internal constructor(
    private val service: OffersService,
    private val executor: Executor
): OffersRemoteDataSource {
    override suspend fun getOffers() = executor.launchInNetworkThread {
        try {
            AppResult.Success(service.getOffers().items)
        }catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}