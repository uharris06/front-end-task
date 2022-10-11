package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetOffersUseCaseTest {
    @Mock
    private lateinit var offersRepositoryImpl: OffersRepositoryImpl

    private lateinit var getOffersUseCase: GetOffersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        getOffersUseCase = GetOffersUseCase(offersRepositoryImpl)
    }

    @Test
    fun shouldDeleteOffer() {
        runBlocking {
            getOffersUseCase.invoke(null)
            verify(offersRepositoryImpl).getOffers()
        }
    }
}