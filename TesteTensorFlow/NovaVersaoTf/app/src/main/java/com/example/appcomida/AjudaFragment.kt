package com.example.appcomida

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import org.tensorflow.lite.Interpreter

class AjudaFragment : Fragment() {

    private var _binding: FragmentAjudaBinding? = null
    private val binding get() = _binding!!

    // Código para abrir a câmera
    private val CAMERA_REQUEST_CODE = 100
    private val CAMERA_PERMISSION_CODE = 101

    // Classe de detecção de frutas
    private lateinit var fruitDetection: FruitDetection
    private lateinit var interpreter: Interpreter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAjudaBinding.inflate(inflater, container, false)

        // Inicializar o modelo TensorFlow Lite
        fruitDetection = FruitDetection(requireContext())
        interpreter = fruitDetection.loadModel()

        // Configura o clique no botão para abrir a câmera
        binding.btnAbrirCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permissão já concedida
                abrirCamera()
            } else {
                // Solicitar permissão
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Recuperar a foto como um Bitmap
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            // Exibir a imagem capturada no ImageView
            binding.imageView.setImageBitmap(photo)

            // Detectar frutas na imagem
            val detectionResults = fruitDetection.detectFruit(photo, interpreter)

            // Exibir os resultados na TextView
            binding.resultTextView.text = detectionResults.joinToString("\n") { "${it.label}: ${it.confidence}" }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, abrir a câmera
                abrirCamera()
            } else {
                // Permissão negada
                Toast.makeText(context, "Permissão da câmera negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
