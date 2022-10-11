package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.source.OffersRemoteDataSource
import com.uharris.frontendtask.utils.AppResult
import com.uharris.frontendtask.utils.Mockable
import javax.inject.Inject

@Mockable
class GetOffersUseCase @Inject internal constructor(
    private val repositoryImpl: OffersRepositoryImpl
): UseCase<AppResult<List<Offer>>, Unit?>() {
    override suspend fun run(params: Unit?) = repositoryImpl.getOffers()
}