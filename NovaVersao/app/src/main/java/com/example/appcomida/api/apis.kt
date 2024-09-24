package com.example.appcomida.api

import com.example.appcomida.ApiService
import com.example.appcomida.dataclass.alimento
import com.example.appcomida.dataclass.compra
import com.example.appcomida.dataclass.user
import com.prolificinteractive.materialcalendarview.CalendarDay
import getRetrofit
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

suspend fun registerCompra(nome: String, qntde: Int) {
    val compra = compra(nome,  qntde)

    // Directly using the IO dispatcher
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.registroCompras(compra) }

        // Handle the response on the Main dispatcher
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val registerCompra = response.body()
                println("Registered Compra: $registerCompra") // Debug log
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
suspend fun registerAlimento(nome: String, cal: Double,esp:String,data:String) {
    val alimento = alimento(nome,cal,esp,data)

    // Directly using the IO dispatcher
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.registroAlimentos(alimento) }

        // Handle the response on the Main dispatcher
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val alimentoR = response.body()
                println("Registered Alimento: $alimentoR") // Debug log
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

suspend fun removeAlimento(nome: String,data:String) {


    // Directly using the IO dispatcher
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.removeAlimento(nome,data) }

        // Handle the response on the Main dispatcher
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val body = response.body()
                println("Registered Alimento: $body") // Debug log
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






