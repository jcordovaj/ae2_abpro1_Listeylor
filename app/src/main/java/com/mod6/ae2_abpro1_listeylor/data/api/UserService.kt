package com.mod6.ae2_abpro1_listeylor.data.api

import com.mod6.ae2_abpro1_listeylor.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}