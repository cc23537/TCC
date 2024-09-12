package com.example.calendarioteste2.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calendarioteste2.databinding.FragmentSlideshowBinding
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private lateinit var calendarView: MaterialCalendarView
    private val redDays = mutableListOf<CalendarDay>()
    private lateinit var vermelhoDecorator: Vermelho

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inicialize calendarView
        calendarView = binding.calendarView

        vermelhoDecorator = Vermelho(redDays)
        calendarView.addDecorator(vermelhoDecorator)

        // Chama a função que buscar as datas da API
        DataAPI()

        return root
    }

    private fun DataAPI() {

        val apiDates = listOf("2024-09-15", "2024-09-18") // Exemplo de datas recebidas

        // Convertendo as strings recebidas para o tipo CalendarDay
        apiDates.forEach { dateString ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}