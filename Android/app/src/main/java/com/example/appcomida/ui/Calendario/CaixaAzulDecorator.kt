import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay

class CaixaAzulDecorator(
    private val dias: List<CalendarDay>,
    private val drawable: Drawable
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        // Verifica se o dia está na lista de dias a serem decorados
        return dias.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        // Define o fundo personalizado
        view.setBackgroundDrawable(drawable)
    }
}
