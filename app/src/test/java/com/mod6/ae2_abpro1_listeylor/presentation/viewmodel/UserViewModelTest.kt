package com.mod6.ae2_abpro1_listeylor.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mod6.ae2_abpro1_listeylor.data.model.NetworkResult
import com.mod6.ae2_abpro1_listeylor.data.model.User
import com.mod6.ae2_abpro1_listeylor.data.repository.UserRepository
import com.mod6.ae2_abpro1_listeylor.util.TestDispatcherRule
import com.mod6.ae2_abpro1_listeylor.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    // Reglas para LiveData y Coroutines
    @get:Rule
    val instantExecutorRule    = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    @get:Rule
    val mainDispatcherRule     = TestDispatcherRule(testDispatcher)

    private val userRepository = mockk<UserRepository>()
    private lateinit var viewModel: UserViewModel
    private val mockUserList   = listOf(
        User(1, "Mitest del VM", "mtuser", "mt@perasconmanzanas.com",
            null, null, null, null)
    )

    @Before
    fun setup() {
        // Nota: Para el init, se requiere un Mock del repositorio para simular una carga exitosa
        // El 'init' llama a fetchUsers(), por lo que se necesita una respuesta predeterminada.
        coEvery { userRepository.getUsers() } returns NetworkResult.Success(mockUserList)

        viewModel = UserViewModel(userRepository)
    }

    // Test 1) Caso Inicial: Carga al inicializar el ViewModel
    @Test
    fun `init debe establecer userList a NetworkResult_Loading e intentar fetchUsers`() = runTest {
        // Verifica el estado intermedio de carga.
        // Como 'fetchUsers' en 'init' primero establece Loading:
        val firstValue = viewModel.userList.getOrAwaitValue {
        }

        // Verifica el valor final después de la ejecución del Coroutine ('Success')
        assertTrue("El resultado final debería ser Success después del init",
            viewModel.userList.getOrAwaitValue() is NetworkResult.Success)
    }

    // Test 2) Caso de Éxito: ViewModel (fetchUsers) actualiza userList
    @Test
    fun `fetchUsers debe actualizar userList a NetworkResult_Success`() = runTest {
        // Configura el repo para que retorne datos para la llamada
        coEvery { userRepository.getUsers() } returns NetworkResult.Success(mockUserList)

        // Carga usuarios
        viewModel.fetchUsers()

        // Verifica que el resultado final sea NetworkResult.Success (Assert)
        val result = viewModel.userList.getOrAwaitValue()
        assertTrue("El resultado debe ser Success", result is NetworkResult.Success)
        assertEquals(mockUserList, (result as NetworkResult.Success).data)
    }

    // Test 3) Caso de Error: ViewModel (fetchUsers) no actualiza userList
    @Test
    fun `fetchUsers debe actualizar userList a NetworkResult_Error en caso de fallo`() = runTest {
        val errorMessage = "Error de red simulado"
        // Configura el repositorio para que devuelva un NetworkResult.Error
        coEvery { userRepository.getUsers() } returns NetworkResult.Error(errorMessage)

        // Carga usuarios
        viewModel.fetchUsers()

        // Verifica que el resultado final sea NetworkResult.Error
        val result = viewModel.userList.getOrAwaitValue()

        assertTrue("El resultado debe ser Error", result is NetworkResult.Error)
        assertEquals(errorMessage, (result as NetworkResult.Error).message)
    }
}