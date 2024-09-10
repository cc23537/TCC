package com.example.calendarioteste2.ui.slideshow

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*

class Vermelho(private val date: Calendar) : DayViewDecorator{
    override fun shouldDecorate(day: CalendarDay): Boolean {
        // Verifica se o dia do calendário coincide com a data especificada
        val calendar = Calendar.getInstance()
        calendar.time = day.date
        return calendar.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
    }

    override fun decorate(view: DayViewFacade) {
        // Adiciona um ponto vermelho ao dia
        view.addSpan(LineSpan(Color.RED, 3f, 50f)) // Altere para BackgroundSpan se quiser alterar o fundo
    }
}