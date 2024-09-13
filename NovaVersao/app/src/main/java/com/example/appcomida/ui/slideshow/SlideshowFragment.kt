package com.example.appcomida.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcomida.AddAlimentosDialogFragment
import com.example.appcomida.AddListaDialogFragment
import com.example.appcomida.databinding.FragmentSlideshowBinding
import com.example.appcomida.dataclass.alimento
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.appcomida.ApiService

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

        // Inicialize calendarView
        calendarView = binding.calendarView
        vermelhoDecorator = LinhaVermelha(redDays)
        calendarView.addDecorator(vermelhoDecorator)

        // Busca os alimentos e atualiza o calendário
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
                }
            }

            override fun onFailure(call: Call<List<alimento>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun updateCalendar(apiData: List<String>) {
        redDays.clear()
        apiData.forEach { dateString ->
            val dateParts = dateString.split("-")
            val year = dateParts[0].toInt()
            val month = dateParts[1].toInt() - 1 // CalendarDay usa mês de 0 a 11
            val day = dateParts[2].toInt()
            val calendarDay = CalendarDay.from(year, month, day)

            // Adiciona a data recebida à lista de dias vermelhos
            redDays.add(calendarDay)
        }

        // Atualiza o decorador
        calendarView.invalidateDecorators()
    }

    private fun showDateInfoDialog(date: CalendarDay) {
        val message = "Você clicou em ${date.date.toString()}"

        AlertDialog.Builder(requireContext())
            .setTitle("Informações do Dia")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.floatingAddAlimentos.setOnClickListener{
            val add = AddAlimentosDialogFragment()
            add.show(parentFragmentManager, "AddDialog")

        }
    }
}