package com.example.appcomida

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.appcomida.api.registerCompra
import com.example.appcomida.api.registerUser
import com.example.appcomida.databinding.FragmentAddAlimentosDialogBinding
import com.example.appcomida.databinding.FragmentAddListaDialogBinding
import kotlinx.coroutines.launch

class AddAlimentosDialogFragment : DialogFragment() {

    private var _binding: FragmentAddAlimentosDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAlimentosDialogBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = true

        binding.btnAdicionarAliemto.setOnClickListener {
            val nomeAlimento = binding.edtNomeAddAlimento.text.toString()
            val calorias = binding.edtCalorias.text.toString()
            val especificacoes = binding.edtEspecifi.text.toString()
            val validade = binding.edtValidade.text.toString()


            lifecycleScope.launch {
                try {
                    //registerAlimeto(nomeAlimento, calorias, especificacoes, validade)
                } catch (e: Exception) {
                    // Handle the exception
                }
                dismiss()
            }
        }
    }


}