package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentListadeComprasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ListadeComprasFragment : Fragment() {
    private lateinit var binding: FragmentListadeComprasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentListadeComprasBinding.inflate(inflater, container, false)
        return binding!!.root
        }

    private fun ListagemCompras() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = getRetrofit().create(RegistroFragment.ApiService::class.java)
                val response = apiService.listagemCompras()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val listagem = response.body()
                        println("listagem: $listagem") // Log de depuração
                    } else {
                        val errorMessage = "Failed: ${response.code()} - ${response.errorBody()?.string()}"
                        println(errorMessage) // Log de depuração
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    println("IOException: ${e.message}") // Log de depuração
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    println("Exception: ${e.message}") // Log de depuração
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}