package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.utils.Mockable
import javax.inject.Inject

@Mockable
class SaveOfferUseCase @Inject internal constructor(
    private val repositoryImpl: OffersRepositoryImpl
) : UseCase<Unit, Offer>() {
    override suspend fun run(params: Offer) {
        repositoryImpl.saveOfferBasket(params)
    }
}