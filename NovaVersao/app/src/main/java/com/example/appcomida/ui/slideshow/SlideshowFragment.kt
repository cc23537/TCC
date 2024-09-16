package com.example.appcomida.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcomida.AddAlimentosDialogFragment
import com.example.appcomida.databinding.FragmentSlideshowBinding
import com.example.appcomida.dataclass.alimento
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.appcomida.ApiService
import getRetrofit




class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendarView: MaterialCalendarView
    private val redDays = mutableListOf<CalendarDay>()
    private lateinit var vermelhoDecorator: LinhaVermelha

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
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
                        val apiData = alimentos.map { it.validade }
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
            val dateParts = dateString.split("-")
            val year = dateParts[0].toInt()
            val month = dateParts[1].toInt() - 1
            val day = dateParts[2].toInt()
            val calendarDay = CalendarDay.from(year, month, day)


            redDays.add(calendarDay)
        }

        calendarView.invalidateDecorators()
    }

    private fun showDateInfoDialog(date: CalendarDay) {
        val service = getRetrofit().create(ApiService::class.java)

        service.getAllAlimentos().enqueue(object : Callback<List<alimento>> {
            override fun onResponse(call: Call<List<alimento>>, response: Response<List<alimento>>) {
                if (response.isSuccessful) {
                    response.body()?.let { alimentos ->

                        val alimentosNoDia = alimentos.filter { alimento ->
                            val dateParts = alimento.validade.split("-")
                            val year = dateParts[0].toInt()
                            val month = dateParts[1].toInt() - 1
                            val day = dateParts[2].toInt()

                            val validadeDate = CalendarDay.from(year, month, day)
                            validadeDate == date
                        }

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
                        val message = if (alimentosNoDia.isNotEmpty()) {
                            alimentosNoDia.joinToString("\n") { alimento ->
                                """
                                    
                               
                            ➡️ **NOME**: ${alimento.nomeAlimento}
                            🔥 CALORIAS: ${alimento.calorias} kcal
                            📝 ESPECIFICAÇÕES: ${alimento.especificacoes}
                            🗓️ VALIDADE: ${alimento.validade}
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
}