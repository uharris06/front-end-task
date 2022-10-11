package com.uharris.frontendtask.data.source

import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.data.preferences.OffersPreferences
import com.uharris.frontendtask.data.source.OffersLocalDataSource
import com.uharris.frontendtask.data.source.OffersLocalDataSourceImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class OffersLocalDataSourceImplTest {

    @Mock
    private lateinit var offersPreferences: OffersPreferences

    @Mock
    private lateinit var offer: Offer

    private lateinit var offersLocalDataSource: OffersLocalDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        offersLocalDataSource = OffersLocalDataSourceImpl(offersPreferences)
    }

    @Test
    fun shouldSaveOffer() {
        offersLocalDataSource.saveOfferBasket(offer)
        verify(offersPreferences).saveOffer(offer)
    }

    @Test
    fun shouldDeleteOffer() {
        offersLocalDataSource.deleteOfferBasket(offer)
        verify(offersPreferences).deleteOffer(offer)
    }

    @Test
    fun shouldReturnListOffersSaved() {
        val list = listOf(offer)
        whenever(offersPreferences.getOffers()).thenReturn(list)
        val offers = offersLocalDataSource.getOffersFromBasket()
        verify(offersPreferences).getOffers()
        assert(list.size == offers.size)
    }
}