package com.mod6.ae2_abpro1_listeylor.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mod6.ae2_abpro1_listeylor.R
import com.mod6.ae2_abpro1_listeylor.data.api.RetrofitClient
import com.mod6.ae2_abpro1_listeylor.data.model.NetworkResult
import com.mod6.ae2_abpro1_listeylor.data.repository.UserRepository
import com.mod6.ae2_abpro1_listeylor.databinding.UsersListFragmentBinding
import com.mod6.ae2_abpro1_listeylor.presentation.adapter.UserAdapter
import com.mod6.ae2_abpro1_listeylor.presentation.viewmodel.UserViewModel
import com.mod6.ae2_abpro1_listeylor.presentation.viewmodel.UserViewModelFactory

class UsersListFragment : Fragment(R.layout.users_list_fragment) {

    private var _binding: UsersListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter

    // Inicialización simplificada del ViewModel (en un entorno real usaríamos Hilt/Koin)
    private val viewModel: UserViewModel by viewModels {
        val userService = RetrofitClient.userService // Asume RetrofitClient está configurado
        val repository = UserRepository(userService)
        UserViewModelFactory(repository) // Necesitas crear esta Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = UsersListFragmentBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            // Navegar al detalle pasando el objeto User
            val action = UsersListFragmentDirections.actionUsersListFragmentToUserDetailFragment(user)
            findNavController().navigate(action)
        }
        binding.rvUsers.adapter = userAdapter
    }

    private fun observeViewModel() {
        viewModel.userList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // Mostrar ProgressBar y ocultar RecyclerView/Error
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvUsers.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is NetworkResult.Success -> {
                    // Actualizar UI con datos
                    binding.progressBar.visibility = View.GONE
                    binding.rvUsers.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    userAdapter.submitList(result.data)
                }
                is NetworkResult.Error -> {
                    // Mostrar mensaje de error
                    binding.progressBar.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Error: ${result.message}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}