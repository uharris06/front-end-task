package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import com.uharris.frontendtask.data.dto.Offer
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class DeleteOfferUseCaseTest {

    @Mock
    private lateinit var offer: Offer

    @Mock
    private lateinit var offersRepositoryImpl: OffersRepositoryImpl

    private lateinit var deleteOfferUseCase: DeleteOfferUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        deleteOfferUseCase = DeleteOfferUseCase(offersRepositoryImpl)
    }

    @Test
    fun shouldDeleteOffer() {
        runBlocking {
            deleteOfferUseCase.invoke(offer)
            verify(offersRepositoryImpl).deleteOfferBasket(offer)
        }
    }
}