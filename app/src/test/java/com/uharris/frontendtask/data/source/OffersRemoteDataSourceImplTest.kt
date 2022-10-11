package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.dto.OfferResponse
import com.uharris.frontendtask.data.services.OffersService
import com.uharris.frontendtask.data.source.OffersRemoteDataSource
import com.uharris.frontendtask.data.source.OffersRemoteDataSourceImpl
import com.uharris.frontendtask.utils.AppResult
import com.uharris.frontendtask.utils.TestExecutor
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException
import java.lang.Exception
import kotlin.jvm.Throws

class OffersRemoteDataSourceImplTest {

    @Mock
    private lateinit var offer: Offer

    @Mock
    private lateinit var offerResponse: OfferResponse

    @Mock
    private lateinit var service: OffersService

    private var testExecutor = TestExecutor()

    private lateinit var offersRemoteDataSource: OffersRemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        offersRemoteDataSource = OffersRemoteDataSourceImpl(service, testExecutor)
    }

    @Test
    fun shouldReturnListOffers() {
        runBlocking {
            val list = listOf(offer)
            whenever(service.getOffers()).thenReturn(offerResponse)
            whenever(offerResponse.items).thenReturn(list)
            val offers = offersRemoteDataSource.getOffers()
            verify(service).getOffers()
            assert(offers is AppResult.Success)
            assert((offers as AppResult.Success).data.size == list.size)
        }
    }

    @Test
    @Throws(RuntimeException::class)
    fun shouldThrowException() {
        runBlocking {
            whenever(service.getOffers()).thenThrow(RuntimeException("Exception"))
            val offers = offersRemoteDataSource.getOffers()
            verify(service).getOffers()
            assert(offers is AppResult.Error)
        }
    }
}