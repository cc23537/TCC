package com.example.appcomida.ui.lista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcomida.ApiService
import com.example.appcomida.databinding.FragmentListaBinding
import com.example.appcomida.dataclass.compra

import getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel = ViewModelProvider(this).get(ListaViewModel::class.java)
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCompras()

        binding.floatingActionButton.setOnClickListener {
            // Lógica para o botão de ação flutuante
        }
    }

    private fun fetchCompras() {
        val retrofit = getRetrofit() // Certifique-se de que o método getRetrofit está configurado corretamente
        val apiService = retrofit.create(ApiService::class.java)

        apiService.listagemCompras().enqueue(object : Callback<List<compra>> {
            override fun onResponse(call: Call<List<compra>>, response: Response<List<compra>>) {
                if (response.isSuccessful) {
                    val compras = response.body()
                    if (compras != null) {
                        binding.textView4.text = compras[0].alimentoASerComprado
                    }
                } else {
                    showError("Erro ao obter dados")
                }
            }

            override fun onFailure(call: Call<List<compra>>, t: Throwable) {
                showError("Falha na solicitação: ${t.message}")
            }
        })

    }



    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}