package com.mod6.ae2_abpro1_listeylor.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        // Antes de cada test: Reemplaza el Dispatcher.Main por el Dispatcher de prueba.
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        // Después de cada test: Restablece el Dispatcher.Main.
        Dispatchers.resetMain()
    }
}