package com.uharris.frontendtask.utils

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

inline fun <reified T> List<T>.toJson(): String = GsonBuilder().apply {
    serializeNulls()
    setPrettyPrinting()
}.create().toJson(this)

inline fun <reified T> String.toList(): T = GsonBuilder().apply {
    serializeNulls()
    setPrettyPrinting()
}.create().fromJson(this, object: TypeToken<T>() {}.type )