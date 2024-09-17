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

    // Remove @RequiresApi annotation if targeting API level 26 and above
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateToISO(dateString: String): String? {
        return try {
            // Define the format of the input date (adjust as necessary)
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            // Define the output format
            val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE

            // Convert the string to LocalDate
            val date = LocalDate.parse(dateString, inputFormatter)
            // Format LocalDate to ISO format
            date.format(outputFormatter)
        } catch (e: Exception) {
            // Log the error or handle it as needed
            null
        }
    }
}