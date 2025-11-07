package com.mod6.ae2_abpro1_listeylor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura la barra de acción superior (App Bar)
        supportActionBar?.title = "Listeylor"

        // Configura Navigation Component
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configura el menú inferior (BottomNavigationView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)

        // Configura el botón de 'Regresar' en el menú inferior
        bottomNav.menu.findItem(R.id.action_back).setOnMenuItemClickListener {
            navController.navigateUp() // Simula el botón de atrás
            true
        }
    }
}