package com.example.appcomida

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)

        // Configuração de layout e insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuração do NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.frameLayout) as NavHostFragment
        val navController = navHostFragment.navController

        // Observe o destino atual para decidir qual ação tomar após o SplashFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment) {
                // Simulação de lógica para decidir navegação
                val isUserLoggedIn = checkUserLoginStatus()

                if (isUserLoggedIn) {
                    navController.navigate(R.id.action_splashFragment_to_nav_home)
                } else {
                    navController.navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }
    }

    private fun checkUserLoginStatus(): Boolean {
        // Implementação para verificar se o usuário está logado
        // Por exemplo, pode verificar se há um token salvo nas preferências compartilhadas
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    private fun enableEdgeToEdge() {
        // Método para habilitar o layout em edge-to-edge, se necessário
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

}