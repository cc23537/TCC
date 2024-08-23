package com.example.appcomida.api

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.appcomida.ApiService
import com.example.appcomida.R
import com.example.appcomida.dataclass.user
import com.example.appcomida.getRetrofit
import kotlinx.coroutines.*
import retrofit2.HttpException


import java.io.IOException


suspend fun registerUser(nome: String, email: String, password: String) {
    val user = user(nome, email, password)

    // Directly using the IO dispatcher
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.register(user) }

        // Handle the response on the Main dispatcher
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val registeredUser = response.body()
                println("Registered User: $registeredUser") // Debug log
            } else {
                val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                println(errorMessage) // Debug log
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("IOException: ${e.message}") // Debug log
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("HttpException: ${e.message()}") // Debug log
        }
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("Exception: ${e.message}") // Debug log
        }
    }
}
