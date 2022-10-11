package com.uharris.frontendtask.utils

interface Executor {
    suspend fun <T> launchInNetworkThread(block: suspend () -> T) : T
}