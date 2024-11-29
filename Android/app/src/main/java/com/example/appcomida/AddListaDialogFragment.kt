package com.example.appcomida

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.example.appcomida.api.registerCompra
import com.example.appcomida.api.registerUser
import com.example.appcomida.databinding.FragmentAddListaDialogBinding
import com.example.appcomida.ui.lista.RvLista
import kotlinx.coroutines.launch


class AddListaDialogFragment : DialogFragment() {

    private var _binding: FragmentAddListaDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: RvLista

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListaDialogBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = true

        binding.btnAddC.setOnClickListener {
            lifecycleScope.launch {
                val nomeCompra = binding.edtDescCompra.text.toString()
                val quantidade = binding.edtQnt.text.toString()
                val desc = quantidade.toInt()

                try {
                    registerCompra(nomeCompra,  desc)
                    setFragmentResult("adicionarAlimento", Bundle())
                } catch (e: Exception) {
                    // Handle the exception
                }
                dismiss()
            }
        }

        binding.btnFechar.setOnClickListener{
            dismiss()
        }

    }



}