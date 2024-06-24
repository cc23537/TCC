package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.Response
import java.io.IOException
import java.security.SecureRandom
import javax.net.ssl.*
import java.security.cert.X509Certificate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Path


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtPassworld.text.toString()
            fetchData(email,senha)
        }

    }

    private fun fetchData(email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiService = getRetrofit().create(ApiService::class.java)
                val response = apiService.login(email, password)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val message = response.body()
                        println("Response Body: $message") // Log de depuração
                        //findNavController().navigate(R.id.action_loginFragment_to_mobile_navigation)
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
            .baseUrl("http://10.0.2.2:8080/") // URL base para o servidor local acessível pelo emulador
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
    interface ApiService {
        @GET("cliente/{email}/{senha}")
        suspend fun login(@Path("email") email: String, @Path("senha") password: String): Response<String>
    }


}

object UnsafeOkHttpClient {
    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Cria um trust manager que não valida cadeias de certificados
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Instala o trust manager que confia em todos os certificados
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Cria uma fábrica de sockets SSL com nosso trust manager que confia em todos os certificados
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}