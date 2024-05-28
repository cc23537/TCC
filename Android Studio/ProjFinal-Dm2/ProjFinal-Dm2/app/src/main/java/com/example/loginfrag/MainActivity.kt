package com.example.loginfrag

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginfrag.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.auth
import java.io.FileInputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializaFirebase()
    }

    private fun inicializaFirebase(){
        //var serviceAccount = FileInputStream("../firebase/loginfrag.json");
        //var options = FirebaseOptions.Builder
        FirebaseApp.initializeApp(applicationContext)
    }

    override fun onDestroy() {

        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            Firebase.auth.signOut()
        }

        super.onDestroy()
    }
}