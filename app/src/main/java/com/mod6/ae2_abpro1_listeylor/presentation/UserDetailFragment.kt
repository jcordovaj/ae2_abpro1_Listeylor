package com.mod6.ae2_abpro1_listeylor.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mod6.ae2_abpro1_listeylor.R
import com.mod6.ae2_abpro1_listeylor.databinding.UserDetailFragmentBinding
import com.mod6.ae2_abpro1_listeylor.data.model.User

class UserDetailFragment : Fragment(R.layout.user_detail_fragment) {

    private var _binding: UserDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializa View Binding
        _binding = UserDetailFragmentBinding.bind(view)

        // Obtiene el objeto User
        val user = args.user

        // Trae TODOS los detalles del usuario
        displayUserDetails(user)
    }

    private fun displayUserDetails(user: User) {

        // Maneja datos nulos
        val safeString = { value: String? -> value ?: "No Disponible" }

        // Datos básicos
        binding.tvDetailName.text       = user.name
        binding.tvDetailIdUsername.text = "ID: ${user.id} | Usuario: ${safeString(user.username)}"

        // Contacto
        binding.tvDetailEmail.text   = "Correo: ${safeString(user.email)}"
        binding.tvDetailPhone.text   = "Teléfono: ${safeString(user.phone)}"
        binding.tvDetailWebsite.text = "Web: ${safeString(user.website)}"

        // Domicilio
        val street  = safeString(user.address?.street)
        val suite   = safeString(user.address?.suite)
        val city    = safeString(user.address?.city)
        val zipcode = safeString(user.address?.zipcode)

        binding.tvDetailAddressStreetSuite.text = "Calle y Suite: $street, $suite"
        binding.tvDetailAddressCityZip.text     = "Ciudad y Código Postal: $city $zipcode"

        // Empresa
        binding.tvDetailCompanyName.text = "Nombre: ${safeString(user.company?.name)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}