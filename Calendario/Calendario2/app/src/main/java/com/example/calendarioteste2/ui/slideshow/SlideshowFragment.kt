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

        // Configure o listener para o clique em datas
        calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                handleDateClick(date)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleDateClick(date: CalendarDay) {
        if (::calendarView.isInitialized) {
            if (redDays.contains(date)) {
                vermelhoDecorator.removeDate(date)
            } else {
                vermelhoDecorator.addDate(date)
            }
            calendarView.invalidateDecorators() // Atualize a visualização do calendário

            // Exiba o diálogo com as informações do dia
            showDateInfoDialog(date)
        }
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