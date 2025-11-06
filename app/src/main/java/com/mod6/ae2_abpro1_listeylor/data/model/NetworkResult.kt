package com.mod6.ae2_abpro1_listeylor.data.model

// Maneja el estado de la red (Éxito, Error, Cargando)
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}