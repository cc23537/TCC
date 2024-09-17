package com.example.appcomida

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.appcomida.api.registerAlimento
import com.example.appcomida.databinding.FragmentAddAlimentosDialogBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = true

        binding.btnAdicionarAliemto.setOnClickListener {
            lifecycleScope.launch {
                val nomeAlimento = binding.edtNomeAddAlimento.text.toString()
                val calorias = binding.edtCalorias.text.toString()
                val especificacoes = binding.edtEspecifi.text.toString()
                val validade = binding.edtValidade.text.toString()

                try {

                    val formattedDate = formatDateToISO(validade) ?: "Invalid date"
                    registerAlimento(nomeAlimento, calorias.toDouble(), especificacoes, formattedDate)
                } catch (e: Exception) {
                }
                dismiss()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateToISO(dateString: String): String? {
        return try {
            // o input
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            // o formato
            val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE

            // str pra date
            val date = LocalDate.parse(dateString, inputFormatter)
            // formata
            date.format(outputFormatter)
        } catch (e: Exception) {

            null
        }
    }
}