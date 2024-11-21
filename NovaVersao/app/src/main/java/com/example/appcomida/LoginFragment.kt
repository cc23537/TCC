package com.example.appcomida

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appcomida.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtPassworld.text.toString()

            fetchData(email, senha)
        }
        binding.btnReg.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
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
                        println("Response Body: $message")
                        saveEmail(email)
                        //saveLogin()
                        //findNavController().navigate(R.id.action_loginFragment_to_nav_home)//action_loginFragment_to_nav_home
                        (activity as MainActivity).navigateToMain()
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
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    println("Exception: ${e.message}")
                }
            }
        }
    }

    private fun saveEmail(email: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_email", email)
        editor.apply()
    }

    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://03e7-187-106-37-122.ngrok-free.app")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }



    private fun saveLogin() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()       
    }
}

object UnsafeOkHttpClient {
    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
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