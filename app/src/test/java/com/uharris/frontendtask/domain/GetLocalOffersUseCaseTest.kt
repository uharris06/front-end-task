package com.uharris.frontendtask.domain

import com.uharris.frontendtask.data.OffersRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetLocalOffersUseCaseTest {
    @Mock
    private lateinit var offersRepositoryImpl: OffersRepositoryImpl

    private lateinit var getLocalOffersUseCase: GetLocalOffersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        getLocalOffersUseCase = GetLocalOffersUseCase(offersRepositoryImpl)
    }

    @Test
    fun shouldDeleteOffer() {
        runBlocking {
            getLocalOffersUseCase.invoke(null)
            verify(offersRepositoryImpl).getOffersFromBasket()
        }
    }
}