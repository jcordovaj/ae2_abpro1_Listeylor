package com.mod6.ae2_abpro1_listeylor.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Función de extensión que obtiene el valor de un LiveData de forma síncrona.
 * Bloquea el hilo de prueba hasta que se emite un valor, o hasta que se agota el tiempo de espera.
 *
 * @param time Tiempo máximo para esperar (por defecto 2 segundos).
 * @param timeUnit Unidad de tiempo para la espera.
 * @param afterObserve Acción opcional a ejecutar después de iniciar la observación (ej. llamar a fetchUsers()).
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    // Observer del LiveData
    this.observeForever(observer)

    try {
        // Disparará el LiveData, por ejemplo, la llamada al ViewModel
        afterObserve.invoke()

        // Espera hasta que el valor sea emitido (latch.countDown()) o que se agote el tiempo
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set. Waited ${timeUnit.toMillis(time)}ms.")
        }

    } finally {
        // Asegura que el observer se elimine para evitar memory leaks.
        this.removeObserver(observer)
    }

    // devuelve el valor
    @Suppress("UNCHECKED_CAST")
    return data as T
}