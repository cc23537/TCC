package com.example.appcomida

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appcomida.api.registerUser
import com.example.appcomida.databinding.FragmentRegistroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            lifecycleScope.launch {
                try {
                    registerUser(nome, email, password)
                } catch (e: Exception) {
                    // Handle the exception
                }
            }
        }

        binding.btncancel.setOnClickListener {
            findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
        }
    }
}

