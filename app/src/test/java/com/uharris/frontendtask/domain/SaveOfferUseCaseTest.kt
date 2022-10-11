package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import com.uharris.frontendtask.data.dto.Offer
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class SaveOfferUseCaseTest {
    @Mock
    private lateinit var offer: Offer

    @Mock
    private lateinit var offersRepositoryImpl: OffersRepositoryImpl

    private lateinit var saveOfferUseCase: SaveOfferUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        saveOfferUseCase = SaveOfferUseCase(offersRepositoryImpl)
    }

    @Test
    fun shouldDeleteOffer() {
        runBlocking {
            saveOfferUseCase.invoke(offer)
            verify(offersRepositoryImpl).saveOfferBasket(offer)
        }
    }
}