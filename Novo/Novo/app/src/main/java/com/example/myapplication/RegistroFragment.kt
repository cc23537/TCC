package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentRegistroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.IOException

class RegistroFragment : Fragment() {

    private lateinit var binding: FragmentRegistroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassworld.text.toString()
            registerUser(nome,email, password)
        }

        binding.btncancel.setOnClickListener {
            findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
        }
    }

    private fun registerUser(nome:String,email: String, password: String) {
        val user = User(nome,email, password)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = getRetrofit().create(ApiService::class.java)
                val response = apiService.register(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val registeredUser = response.body()
                        println("Registered User: $registeredUser") // Log de depuração
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

    public fun getRetrofit(): Retrofit {
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
    interface ApiService {
        @GET("cliente/{email}/{senha}")
        suspend fun login(@Path("email") email: String, @Path("senha") password: String): Response<String>

        @POST("cliente")
        suspend fun register(@Body user: User): Response<User>

        @POST("compras")
        suspend fun registroCompras(@Body compra: compra):Response<compra>
        @GET("compras")
        suspend fun listagemCompras(): Response<String>

        @POST("alimentos")
        suspend fun registroAlimentos(@Body alimento: alimento):Response<alimento>
        @GET("alimentos")
        suspend fun listagemArmario(): Response<String>
    }
}

data class User(
    val nomeCliente: String,
    val email: String,
    val senha: String
)

data class alimento(
    val nomealimento: String,
)
data class compra(
    val alimentoASerComprado: String,
    val quantidade: Int,
)