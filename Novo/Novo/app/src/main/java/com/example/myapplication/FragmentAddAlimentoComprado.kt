package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentAddAlimentoCompradoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException


class FragmentAddAlimentoComprado : Fragment() {
    private lateinit var binding: FragmentAddAlimentoCompradoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddAlimentoCompradoBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nome = binding.edtNomeAddAlimento.text.toString()
        var qtde = binding.edtQuantidadeAddAlimentos.text.toString().toInt()
        registerCompra(nome,qtde)
    }

    private fun registerCompra(nome:String,qtde: Int) {
        val compra = compra(nome,qtde)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = getRetrofit().create(RegistroFragment.ApiService::class.java)
                val response = apiService.registroCompras(compra)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val compra = response.body()
                        println("Registered Compra: $compra") // Log de depuração
                        findNavController().navigate(R.id.action_registroFragment_to_nav_home)
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
            .baseUrl("http://10.0.2.2:8081/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }




}