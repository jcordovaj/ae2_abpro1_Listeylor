package com.mod6.ae2_abpro1_listeylor.data.repository

import com.mod6.ae2_abpro1_listeylor.data.api.UserService
import com.mod6.ae2_abpro1_listeylor.data.model.NetworkResult
import com.mod6.ae2_abpro1_listeylor.data.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryTest {

    private val userService = mockk<UserService>()
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepository(userService)
    }

    // Test llamada API: Caso éxito
    @Test
    fun `getUsers debe devolver NetworkResult Success si la llamada a la API es exitosa`() {
        runBlocking {
            val mockUserList = listOf(User(1, "Test User", "testuser",
                "tu@perasconmanzanas.com", null, null, null, null))

            coEvery { userService.getUsers() } returns Response.success(mockUserList)

            val result = userRepository.getUsers()

            // Se verifica que sea 'NetworkResult.Success'
            assertTrue(result is NetworkResult.Success)

            // Se comparan los datos
            assertEquals(mockUserList, (result as NetworkResult.Success).data)
            coVerify(exactly = 1) { userService.getUsers() }
        }
    }

    // Test llamada API: Caso error
    @Test
    fun `getUsers debe devolver NetworkResult Error si la llamada a la API falla`() {
        runBlocking {
            val originalErrorMessage = "Network Error"

            // Mensaje a devolver con prefijo
            val expectedFullErrorMessage = "Error de conexión: $originalErrorMessage"

            // Mock de la excepción con mensaje
            coEvery { userService.getUsers() } throws Exception(originalErrorMessage)

            val result = userRepository.getUsers()

            assertTrue(result is NetworkResult.Error)

            // Comparamos contra el mensaje esperado
            assertEquals(expectedFullErrorMessage, (result as NetworkResult.Error).message)
        }
    }
}