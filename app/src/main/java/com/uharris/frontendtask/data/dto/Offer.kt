package com.uharris.frontendtask.data.dto

import com.uharris.frontendtask.utils.Mockable

@Mockable
data class Offer(
    val id: String,
    val title: String,
    val image: String,
    val price: Int,
    var isInBasket: Boolean = false
)