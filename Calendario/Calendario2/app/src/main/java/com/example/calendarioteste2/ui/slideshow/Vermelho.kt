package com.example.calendarioteste2.ui.slideshow

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*

class Vermelho(private val dates: MutableList<CalendarDay>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(LineSpan(Color.RED, 3f, 50f)) // Linha vermelha com largura 50f
    }

    fun addDate(date: CalendarDay) {
        dates.add(date)
    }

    fun removeDate(date: CalendarDay) {
        dates.remove(date)
    }
}