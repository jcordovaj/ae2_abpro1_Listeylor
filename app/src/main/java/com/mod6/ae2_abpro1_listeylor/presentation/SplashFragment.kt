package com.mod6.ae2_abpro1_listeylor.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mod6.ae2_abpro1_listeylor.R
import com.mod6.ae2_abpro1_listeylor.databinding.SplashFragmentBinding

class SplashFragment : Fragment(R.layout.splash_fragment) {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SplashFragmentBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            // Navega usando la acción definida en nav_graph.xml
            val action = SplashFragmentDirections.actionSplashFragmentToUsersListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}