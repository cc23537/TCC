package com.example.appcomida.ui.Calendario

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcomida.AddAlimentosDialogFragment
import com.example.appcomida.dataclass.alimento
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appcomida.ApiService
import com.example.appcomida.databinding.FragmentCalendarioBinding
import com.google.android.material.datepicker.DayViewDecorator
import getRetrofit
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchAlimentoData() {
        val service = getRetrofit().create(ApiService::class.java)

        service.getAllAlimentos().enqueue(object : Callback<List<alimento>> {
            override fun onResponse(call: Call<List<alimento>>, response: Response<List<alimento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { alimentos ->
                        val apiData: List<String> = alimentos.mapNotNull { it.validade }
                        updateCalendar(apiData)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<alimento>>, t: Throwable) {

            }
        })
    }

    private fun updateCalendar(apiData: List<String>) {
        redDays.clear()
        apiData.forEach { dateString ->

            val safeDateString = dateString
            val dateParts = safeDateString.split("-")
            if (dateParts.size == 3) {
                val year = dateParts[0].toIntOrNull() ?: 0
                val month = (dateParts[1].toIntOrNull() ?: 0) - 1
                val day = dateParts[2].toIntOrNull() ?: 0
                val calendarDay = CalendarDay.from(year, month, day)

                redDays.add(calendarDay)
            }
        }
        calendarView.invalidateDecorators()
    }


    private fun showDateInfoDialog(date: CalendarDay) {
        val service = getRetrofit().create(ApiService::class.java)

        service.getAllAlimentos().enqueue(object : Callback<List<alimento>> {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<List<alimento>>, response: Response<List<alimento>>) {
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
                                """
                                                
                            ➡️ **NOME**: ${alimento.nomeAlimento}
                            🔥 CALORIAS: ${alimento.calorias} kcal
                            📝 ESPECIFICAÇÕES: ${alimento.especificacoes}
                            🗓️ VALIDADE: ${alimento.validade}
                            ⏳ DIAS FALTANTES: ${diasFaltantes(date.year, (date.month+1), date.day)} 
                            """.trimIndent()
                            }
                        } else {
                            "Nenhum alimento registrado para esta data."
                        }


                        AlertDialog.Builder(requireContext())
                            .setTitle("Alimentos no dia ${date.day}/${date.month + 1}/${date.year}")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show()
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<alimento>>, t: Throwable) {

            }
        })
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

    private fun handleDateClick(date: CalendarDay) {
        if (::calendarView.isInitialized) {
            showDateInfoDialog(date)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)// Notação para pegar a data atual
    private fun diasFaltantes(year: Int, month: Int, day: Int): Long{
        val dataAtual: LocalDate = LocalDate.now()
        val dataValidade = LocalDate.of(year, month, day)

        val diasRestantes = ChronoUnit.DAYS.between(dataAtual, dataValidade)
        return diasRestantes
    }


}