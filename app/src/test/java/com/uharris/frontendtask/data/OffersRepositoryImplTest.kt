package com.uharris.frontendtask.data

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.source.OffersLocalDataSource
import com.uharris.frontendtask.data.source.OffersRemoteDataSource
import com.uharris.frontendtask.utils.AppResult
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class OffersRepositoryImplTest {

    @Mock
    private lateinit var offer: Offer

    @Mock
    private lateinit var offersRemoteDataSource: OffersRemoteDataSource

    @Mock
    private lateinit var offersLocalDataSource: OffersLocalDataSource

    private lateinit var offersRepositoryImpl: OffersRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        offersRepositoryImpl = OffersRepositoryImpl(offersLocalDataSource, offersRemoteDataSource)
    }

    @Test
    fun shouldSaveOffer() {
        offersRepositoryImpl.saveOfferBasket(offer)
        verify(offersLocalDataSource).saveOfferBasket(offer)
    }

    @Test
    fun shouldDeleteOffer() {
        offersRepositoryImpl.deleteOfferBasket(offer)
        verify(offersLocalDataSource).deleteOfferBasket(offer)
    }

    @Test
    fun shouldReturnOffersFromLocal() {
        val list = listOf(offer)
        whenever(offersLocalDataSource.getOffersFromBasket()).thenReturn(list)
        val offers = offersRepositoryImpl.getOffersFromBasket()
        verify(offersLocalDataSource).getOffersFromBasket()
        assert(offers.size == list.size)
    }

    @Test
    fun shouldReturnOffersFromRemote() {
        runBlocking {
            val list = listOf(offer)
            whenever(offersLocalDataSource.getOffersFromBasket()).thenReturn(list)
            whenever(offersRemoteDataSource.getOffers()).thenReturn(AppResult.Success(list))
            val offers = offersRepositoryImpl.getOffers()
            verify(offersRemoteDataSource).getOffers()
            verify(offersLocalDataSource).getOffersFromBasket()
            assert(offers is AppResult.Success)
            (offers as AppResult.Success).data.apply {
                assert(size == list.size)
            }
        }
    }
}