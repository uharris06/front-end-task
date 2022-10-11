package com.uharris.frontendtask.utils

import kotlinx.coroutines.runBlocking

class TestExecutor: Executor {
    override suspend fun <T> launchInNetworkThread(block: suspend () -> T): T {
        return runBlocking {
            block()
        }
    }
}