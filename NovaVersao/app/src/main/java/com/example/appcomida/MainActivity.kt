package com.example.appcomida

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.example.appcomida.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialmente, esconda o drawer e a toolbar
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.hide()

        // Inicialmente, esconda o drawer e a toolbar
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.hide()

        // Gerenciar a navegação
        val navHostStartFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_start) as NavHostFragment

        navHostStartFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment -> {
                    // Splash e Login não precisam de toolbar ou drawer
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    supportActionBar?.hide()
                }

                R.id.nav_home -> {
                    // Tela principal deve mostrar a toolbar e o drawer
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    supportActionBar?.show()
                }


            }
        }
    }

    fun navigateToMain() {
        // Troca o NavHostFragment de navi_start para mobile_navigation
        val navHostStart = findViewById<FragmentContainerView>(R.id.nav_host_start)
        val navHostMobile = findViewById<FragmentContainerView>(R.id.nav_host_mobile)

        navHostStart.visibility = View.GONE
        navHostMobile.visibility = View.VISIBLE

        // Navega para a tela principal no mobile_navigation
        val navHostMobileFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_mobile) as NavHostFragment
        navHostMobileFragment.navController.navigate(R.id.nav_home)
    }
}