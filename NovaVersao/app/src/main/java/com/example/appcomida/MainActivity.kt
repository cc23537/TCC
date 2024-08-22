package com.example.appcomida

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
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

        // Verifique o status do usuário e navegue entre as telas
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    // Configurações específicas do SplashFragment
                }
                R.id.loginFragment -> {
                    // Configurações específicas do LoginFragment
                }
                R.id.nav_host_fragment_content_main -> {
                    // Mostra a toolbar e o drawer quando a navegação for para a tela principal
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    supportActionBar?.show()
                }
            }
        }

        // Inicia com o SplashFragment
        if (savedInstanceState == null) {
            navController.navigate(R.id.splashFragment)
        }
    }

    fun navigateToLogin() {
        // Função chamada após o SplashFragment para navegar para o LoginFragment
        findNavController(R.id.fragmentContainer).navigate(R.id.action_splashFragment_to_loginFragment)
    }

    fun navigateToMain() {
        // Função chamada após o LoginFragment para navegar para a tela principal
        findNavController(R.id.fragmentContainer).navigate(R.id.action_loginFragment_to_nav_home)
    }
}