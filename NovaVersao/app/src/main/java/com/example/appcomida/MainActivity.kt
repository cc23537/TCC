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

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)

        // Obter o NavHostFragment inicial (nav_host_start)
        val navHostStartFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_start) as NavHostFragment

        // Obter o NavHostFragment principal (nav_host_mobile)
        val navHostMobileFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_mobile) as NavHostFragment

        // Configurar o AppBarConfiguration com todos os destinos de nível superior
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_list, R.id.nav_calendario, R.id.ajudaFragment2, R.id.ReceitasFragment),
            binding.drawerLayout
        )

        // Configure a Toolbar com o NavController correto (nav_host_mobile)
        setupActionBarWithNavController(navHostMobileFragment.navController, appBarConfiguration)

        // Configure o NavigationView com o NavController correto (nav_host_mobile)
        binding.navView.setupWithNavController(navHostMobileFragment.navController)

        // Listener para controlar a visibilidade do Toolbar e Drawer
        navHostStartFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment, R.id.registroFragment -> hideToolbarAndDrawer()
                else -> showToolbarAndDrawer()
            }
        }
    }

    private fun hideToolbarAndDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.hide()
    }

    private fun showToolbarAndDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        supportActionBar?.show()
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

        showToolbarAndDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_mobile)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}