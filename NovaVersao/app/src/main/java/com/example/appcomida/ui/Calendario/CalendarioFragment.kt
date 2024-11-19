package com.example.appcomida.ui.Calendario

import CaixaAzulDecorator
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appcomida.AddAlimentosDialogFragment
import com.example.appcomida.dataclass.Alimento
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appcomida.ApiService
import com.example.appcomida.api.removeAlimento
import com.example.appcomida.databinding.FragmentCalendarioBinding

import getRetrofit
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O) // Notação para pegar a data atual
class CalendarioFragment : Fragment() {

    private var _binding: FragmentCalendarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendarView: MaterialCalendarView
    private val redDays = mutableListOf<CalendarDay>()
    private lateinit var vermelhoDecorator: LinhaVermelha

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarioViewModel =
            ViewModelProvider(this).get(CalendarioViewModel::class.java)

        _binding = FragmentCalendarioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        calendarView = binding.calendarView
        vermelhoDecorator = LinhaVermelha(redDays)
        calendarView.addDecorator(vermelhoDecorator)

        fetchAlimentoData()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Atualiza a pagina
        parentFragmentManager.setFragmentResultListener("addAlimentoRequest", this) { requestKey, bundle ->
            fetchAlimentoData()
        }

        binding.floatingAddAlimentos.setOnClickListener {
            val add = AddAlimentosDialogFragment()
            add.show(parentFragmentManager, "AddDialog")
        }

        calendarView.setOnDateChangedListener { widget, date, selected ->
            handleDateClick(date)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchAlimentoData() {
        val service = getRetrofit().create(ApiService::class.java)

        service.getAllAlimentos().enqueue(object : Callback<List<Alimento>> {

            override fun onResponse(call: Call<List<Alimento>>, response: Response<List<Alimento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { alimentos ->
                        val apiData: List<String> = alimentos.mapNotNull { it.validade }
                        updateCalendar(apiData)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<Alimento>>, t: Throwable) {

            }
        })
    }
    private fun updateCalendar(apiData: List<String>) {
        redDays.clear()
        val azulDays = mutableListOf<CalendarDay>()

        apiData.forEach { dateString ->
            val dateParts = dateString.split("-")
            if (dateParts.size == 3) {
                val year = dateParts[0].toIntOrNull() ?: 0
                val month = (dateParts[1].toIntOrNull() ?: 0) - 1
                val day = dateParts[2].toIntOrNull() ?: 0
                val calendarDay = CalendarDay.from(year, month, day)

                val diasRestantes = diasFaltantes(year, month + 1, day)
                if (diasRestantes >= 0) {
                    azulDays.add(calendarDay) // Adiciona aos dias com a caixa azul
                }
            }
        }

        // Atualiza os decorators
        calendarView.removeDecorators() // Remove decoradores antigos
        calendarView.addDecorator(CaixaAzulDecorator(azulDays, requireContext().getDrawable(R.drawable.caixa_azul)!!))
    }
    private fun showDateInfoDialog(date: CalendarDay) {
        val service = getRetrofit().create(ApiService::class.java)

        service.getAllAlimentos().enqueue(object : Callback<List<Alimento>> {

            override fun onResponse(call: Call<List<Alimento>>, response: Response<List<Alimento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { alimentos ->

                        val alimentosNoDia = alimentos.filter { alimento ->
                            val dateParts = alimento.validade?.split("-") ?: listOf()
                            if (dateParts.size == 3) {
                                val year = dateParts[0].toIntOrNull() ?: 0
                                val month = (dateParts[1].toIntOrNull() ?: 0) - 1
                                val day = dateParts[2].toIntOrNull() ?: 0
                                val validadeDate = CalendarDay.from(year, month, day)
                                validadeDate == date
                            } else {
                                false
                            }
                        }


                        val message = if (alimentosNoDia.isNotEmpty()) {
                            alimentosNoDia.joinToString("\n") { alimento ->
                                val diasRestantes = diasFaltantes(date.year, (date.month + 1), date.day)
                                val diasMensagem = if (diasRestantes < 0) {
                                    "ESSE ALIMENTO JÁ VENCEU"
                                } else {
                                    "⏳ DIAS FALTANTES: $diasRestantes"
                                }

                                """
                                    
                                ➡️ **NOME**: ${alimento.nomeAlimento}
                                🔥 CALORIAS: ${alimento.calorias} kcal
                                📝 ESPECIFICAÇÕES: ${alimento.especificacoes}
                                🗓️ VALIDADE: ${alimento.validade}
                                $diasMensagem
                                """.trimIndent()
                            }
                        } else {
                            "Nenhum alimento registrado para esta data."
                        }

                        AlertDialog.Builder(requireContext())
                            .setTitle("Alimentos no dia ${date.day}/${date.month + 1}/${date.year}")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, null)
                            .setNeutralButton("Remover") { dialog, which ->
                                val alimentosNomes = alimentosNoDia.map { it.nomeAlimento }.toTypedArray()

                                AlertDialog.Builder(requireContext())
                                    .setTitle("Selecione o alimento a remover")
                                    .setItems(alimentosNomes) { _, selectedIndex ->
                                        val alimentoSelecionado = alimentosNoDia[selectedIndex]

                                        AlertDialog.Builder(requireContext())
                                            .setTitle("Confirmar remoção")
                                            .setMessage("Deseja remover o alimento ${alimentoSelecionado.nomeAlimento}?")
                                            .setPositiveButton("Sim") { _, _ ->
                                                viewLifecycleOwner.lifecycleScope.launch {
                                                    removeAlimento(alimentoSelecionado.nomeAlimento, alimentoSelecionado.validade)

                                                    if (response.isSuccessful) {
                                                        fetchAlimentoData()

                                                        Toast.makeText(requireContext(), "Alimento removido com sucesso!", Toast.LENGTH_SHORT).show()
                                                    } else {
                                                        Toast.makeText(requireContext(), "Falha ao remover o alimento.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }

                                            }
                                            .setNegativeButton("Não", null)
                                            .show()
                                    }
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .show()
                            }
                            .show()
                    }
                }
            }


            override fun onFailure(call: Call<List<Alimento>>, t: Throwable) {
            }


        })
    }

    private fun handleDateClick(date: CalendarDay) {
        if (::calendarView.isInitialized) {
            showDateInfoDialog(date)
        }
    }
    private fun diasFaltantes(year: Int, month: Int, day: Int): Long{
        val dataAtual: LocalDate = LocalDate.now()
        val dataValidade = LocalDate.of(year, month, day)

        val diasRestantes = ChronoUnit.DAYS.between(dataAtual, dataValidade) // Calcula a Diferença de Dias
        return diasRestantes
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

