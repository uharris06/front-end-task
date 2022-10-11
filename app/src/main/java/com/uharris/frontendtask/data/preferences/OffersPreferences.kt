package com.uharris.frontendtask.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.uharris.frontendtask.data.dto.Offer
import com.uharris.frontendtask.utils.Mockable
import com.uharris.frontendtask.utils.toJson
import com.uharris.frontendtask.utils.toList
import javax.inject.Inject

@Mockable
class OffersPreferences @Inject internal constructor(context: Context){

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun saveOffer(offer: Offer) {
        getOffers().also {
            sharedPreferences.edit {
                val list = it + listOf(offer)
                putString(OFFERS, list.toJson())
            }
        }
    }

    fun deleteOffer(offer: Offer) {
        getOffers().also {
            sharedPreferences.edit {
                val list = it.fold(arrayListOf<Offer>()) { acc, item ->
                    if(item.id != offer.id) {
                        acc.add(item)
                    }
                    acc
                }
                putString(OFFERS, list.toJson())
            }
        }
    }

    fun getOffers(): List<Offer> = sharedPreferences.getString(OFFERS, "[]")!!.toList()


    companion object {
        private val NAME = OffersPreferences::class.java.canonicalName
        private val OFFERS = "${NAME}_OFFERS"
    }
}