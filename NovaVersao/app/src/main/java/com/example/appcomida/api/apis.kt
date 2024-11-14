package com.example.appcomida.api

import com.example.appcomida.ApiService
import com.example.appcomida.dataclass.Alimento
import com.example.appcomida.dataclass.Compra
import com.example.appcomida.dataclass.Cliente
import getRetrofit
import kotlinx.coroutines.*
import retrofit2.HttpException


import java.io.IOException


suspend fun registerUser(nome: String, email: String, password: String) {
    val Cliente = Cliente(nome, email, password)
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.register(Cliente) }

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val registeredUser = response.body()
                println("Registered User: $registeredUser")
            } else {
                val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                println(errorMessage)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("IOException: ${e.message}")
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("HttpException: ${e.message()}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("Exception: ${e.message}")
        }
    }
}

suspend fun registerCompra(nome: String, qntde: Int) {
    val compra = Compra(nome,  qntde)

    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.registroCompras(compra) }
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
suspend fun registrarAlimento(nome: String, cal: Double, esp:String, data:String) {
    val alimento = Alimento(nome,cal,esp,data)
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.registroAlimentos(alimento) }

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val alimentoR = response.body()
                println("Registered Alimento: $alimentoR")
            } else {
                val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                println(errorMessage)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("IOException: ${e.message}")
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("HttpException: ${e.message()}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("Exception: ${e.message}")
        }
    }
}

suspend fun removeAlimento(nome: String,data:String) {
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.removeAlimento(nome,data) }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                val body = response.body()
                println("Registered Alimento: $body")
            } else {
                val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                println(errorMessage)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("IOException: ${e.message}")
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("HttpException: ${e.message()}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("Exception: ${e.message}")
        }
    }
}

suspend fun DeleteLista(nome: String,quantidade:Int) {
    try {
        val apiService = getRetrofit().create(ApiService::class.java)
        val response = withContext(Dispatchers.IO) { apiService.removeCompra(nome,quantidade) }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                println("Deleted Alimento")
            } else {
                val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                println(errorMessage)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("IOException: ${e.message}")
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("HttpException: ${e.message()}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        withContext(Dispatchers.Main) {
            println("Exception: ${e.message}")
        }
    }
}






