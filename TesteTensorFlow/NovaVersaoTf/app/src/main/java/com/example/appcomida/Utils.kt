package com.example.appcomida

import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder

object Utils {
    // Função para redimensionar e normalizar a imagem, convertendo para ByteBuffer
    fun preprocessImage(bitmap: Bitmap, imageSize: Int): ByteBuffer {
        // Redimensionar a imagem para o tamanho esperado
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)

        // Criar buffer para armazenar os valores da imagem
        val inputBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3) // 4 bytes por float (RGB)
        inputBuffer.order(ByteOrder.nativeOrder())

        // Extrair os pixels e preencher o buffer
        val pixels = IntArray(imageSize * imageSize)
        resizedBitmap.getPixels(pixels, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

        // Normalizar os valores de cada pixel e colocá-los no ByteBuffer
        for (pixel in pixels) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f

            inputBuffer.putFloat(r)
            inputBuffer.putFloat(g)
            inputBuffer.putFloat(b)
        }

        return inputBuffer
    }

    // Função para processar os resultados da inferência
    fun parseDetectionResults(outputArray: Array<FloatArray>): List<DetectionResult> {
        val detectionResults = mutableListOf<DetectionResult>()

        // Exemplo de interpretação dos resultados
        for (i in outputArray[0].indices) {
            val confidence = outputArray[0][i]
            if (confidence > 0.5) {  // Suponha que resultados com confiança > 0.5 sejam detectados
                detectionResults.add(DetectionResult("Fruta $i", confidence))
            }
        }

        return detectionResults
    }
}

// Classe para armazenar os resultados de detecção
data class DetectionResult(val label: String, val confidence: Float)
