package com.mod6.ae2_abpro1_listeylor.data.repository

import com.mod6.ae2_abpro1_listeylor.data.api.UserService
import com.mod6.ae2_abpro1_listeylor.data.model.NetworkResult
import com.mod6.ae2_abpro1_listeylor.data.model.User

class UserRepository(private val userService: UserService) {
    // Obtiene la lista de usuarios y maneja errores
    suspend fun getUsers(): NetworkResult<List<User>> {
        return try {
            val response = userService.getUsers()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            // Manejo de errores de red (e.g., sin conexión)
            NetworkResult.Error("Error de conexión: ${e.localizedMessage}")
        }
    }
}