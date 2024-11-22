package com.example.appcomida

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appcomida.databinding.FragmentAjudaBinding
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.appcomida.api.registrarAlimento
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.FileNotFoundException
import java.time.LocalDateTime

class AjudaFragment : Fragment() {

    private var _binding: FragmentAjudaBinding? = null
    private val binding get() = _binding!!

    private val CAMERA_REQUEST_CODE = 100
    private val CAMERA_PERMISSION_CODE = 101

    private lateinit var fruitDetection: FruitDetection
    private lateinit var interpreter: Interpreter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAjudaBinding.inflate(inflater, container, false)

        fruitDetection = FruitDetection(requireContext())
        interpreter = fruitDetection.loadModel()

        binding.btnAbrirCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                abrirCamera()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun abrirCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(photo)

            val formattedPhoto = formatarImagem(photo, 224, 224)

            val detectionResults = fruitDetection.detectFruit(formattedPhoto, interpreter)
            binding.resultTextView.text = detectionResults.joinToString("\n") { "${it.label}: ${it.confidence}" }

            binding.resultTextView.text = detectionResults.joinToString("\n") {
                "${it.label}: ${it.confidence}"
            }
            val fruitLabelsWithDates = loadFruitLabelsWithWeeks()
            detectionResults.forEach { result ->
                val label = result.label
                var confidence = result.confidence

                if (confidence > 0.0f) {
                    // Obter a data de validade correspondente
                    val weeks = fruitLabelsWithDates[label]?.toLongOrNull()

                    // Calcular a data de validade a partir da data atual
                    val validade = if (weeks != null) {
                        LocalDateTime.now().plusWeeks(weeks)
                    } else {
                        null
                    }

                    lifecycleScope.launch {
                        registrarAlimento(label, null, null, validade.toString())
                    }
                }
            }

        }
    }
    private fun loadFruitLabelsWithWeeks(): Map<String, String> {
        val assetManager = context?.assets
        val fruitLabelsWithWeeks = mutableMapOf<String, String>()

        try {
            if (assetManager != null) {
                assetManager.open("datadevalidade.txt").bufferedReader().useLines { lines ->
                    lines.forEachIndexed { index, line ->

                        val fruitName = "Alimento${index + 1}"
                        fruitLabelsWithWeeks[fruitName] = line.trim()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw FileNotFoundException("Arquivo datadevalidade.txt não encontrado no diretório assets.")
        }
        return fruitLabelsWithWeeks
    }

    private fun formatarImagem(bitmap: Bitmap, largura: Int, altura: Int): Bitmap {
        // Redimensiona a imagem para as dimensões necessárias pelo modelo
        return createScaledBitmap(bitmap, largura, altura, true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamera()
            } else {
                Toast.makeText(context, "Permissão da câmera negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

