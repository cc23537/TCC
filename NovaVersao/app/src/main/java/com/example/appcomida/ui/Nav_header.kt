package layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appcomida.databinding.ActivityMainBinding
import com.example.appcomida.databinding.NavHeaderMainBinding

class NavHeader(private val context: Context) {

    private lateinit var binding: NavHeaderMainBinding

    fun setupHeader(layoutInflater: LayoutInflater, parent: ViewGroup): View {
        // Inicializa o binding
        binding = NavHeaderMainBinding.inflate(layoutInflater, parent, false)

        // Recupera o e-mail do SharedPreferences
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("user_email", "E-mail não encontrado")

        // Atualiza o texto do TextView de email
        binding.email.text = email

        // Retorna o layout da View
        return binding.root
    }
}