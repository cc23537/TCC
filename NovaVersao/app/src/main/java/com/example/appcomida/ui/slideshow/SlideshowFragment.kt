package com.example.appcomida.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appcomida.databinding.FragmentSlideshowBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        // Chama o metodo que chama a data
        DataAPI()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun DataAPI() {

        val apiData = listOf("2024-09-15", "2024-09-18") // Aqui vai vim a data da api, uma ou mais

        // Convertendo as strings recebidas para o tipo CalendarDay
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
}