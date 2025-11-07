package com.mod6.ae2_abpro1_listeylor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod6.ae2_abpro1_listeylor.data.model.NetworkResult
import com.mod6.ae2_abpro1_listeylor.data.model.User
import com.mod6.ae2_abpro1_listeylor.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userList = MutableLiveData<NetworkResult<List<User>>>()
    val userList: LiveData<NetworkResult<List<User>>> = _userList

    init {
        fetchUsers() // Obtener datos al iniciar el ViewModel
    }

    private fun fetchUsers() {
        _userList.value = NetworkResult.Loading // Muestra estado de carga

        viewModelScope.launch {
            val result = repository.getUsers()
            _userList.postValue(result) // Actualiza el LiveData con el resultado
        }
    }
}