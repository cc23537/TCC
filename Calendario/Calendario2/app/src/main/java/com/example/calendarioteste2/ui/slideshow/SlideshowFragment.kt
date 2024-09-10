package com.example.calendarioteste2.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.calendarioteste2.databinding.FragmentSlideshowBinding
import java.util.Calendar

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Obtenha o calendário
        val calendarView = binding.calendarView

        // Defina o dia específico que deseja marcar
        val specificDate = Calendar.getInstance()
        specificDate.set(2024, Calendar.SEPTEMBER, 10) // Defina o ano, mês e dia desejado (exemplo: 10 de setembro de 2024)

        // Adicione o decorador ao MaterialCalendarView
        calendarView.addDecorator(Vermelho(specificDate))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}