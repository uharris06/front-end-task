package com.uharris.frontendtask.data

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.source.OffersLocalDataSource
import com.uharris.frontendtask.data.source.OffersRemoteDataSource
import com.uharris.frontendtask.utils.AppResult
import com.uharris.frontendtask.utils.Executor
import com.uharris.frontendtask.utils.Mockable
import javax.inject.Inject

@Mockable
class OffersRepositoryImpl @Inject internal constructor(
    private val offersLocalDataSource: OffersLocalDataSource,
    private val offersRemoteDataSource: OffersRemoteDataSource
): OffersRemoteDataSource, OffersLocalDataSource{
    override fun saveOfferBasket(offer: Offer) = offersLocalDataSource.saveOfferBasket(offer)

    override fun deleteOfferBasket(offer: Offer) = offersLocalDataSource.deleteOfferBasket(offer)

    override fun getOffersFromBasket() = offersLocalDataSource.getOffersFromBasket()

    override suspend fun getOffers(): AppResult<List<Offer>> {
        return try {
            offersRemoteDataSource.getOffers().apply {
                if (this is AppResult.Success) {
                    this.data.map {
                        it.isInBasket = getOffersFromBasket().any { localOffer -> localOffer.id == it.id }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(e)
        }
    }
}