package com.example.appcomida

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class FruitDetection(private val context: Context) {

    // Carrega o modelo TensorFlow Lite
    fun loadModel(): Interpreter {
        val assetManager = context.assets
        val modelDescriptor = assetManager.openFd("model_unquant.tflite")
        val inputStream = modelDescriptor.createInputStream()
        val mappedByteBuffer = inputStream.use {
            it.channel.map(FileChannel.MapMode.READ_ONLY, modelDescriptor.startOffset, modelDescriptor.declaredLength)
        }

        // Inicializa o interpretador do TensorFlow Lite
        return Interpreter(mappedByteBuffer)
    }

    // Detecta frutas na imagem usando o modelo TensorFlow Lite
    fun detectFruit(bitmap: Bitmap, interpreter: Interpreter): List<DetectionResult> {
        val imageSize = 224  // Suponha que o modelo espere uma imagem 300x300
        val channels = 3     // RGB

        // Pré-processar a imagem
        val inputBuffer: ByteBuffer = preprocessImage(bitmap, imageSize)

        // Criar array de saída para armazenar os resultados da inferência
        val outputArray = Array(1) { FloatArray(13) }  // Exemplo: 10 categorias de frutas

        // Executar a inferência
        interpreter.run(inputBuffer, outputArray)

        // Processar os resultados
        return parseDetectionResults(outputArray)
    }

    // Função para pré-processar a imagem
    fun preprocessImage(bitmap: Bitmap, imageSize: Int): ByteBuffer {
        // Redimensiona o bitmap para as dimensões esperadas
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)

        // Crie o ByteBuffer com o tamanho exato para Float32
        val inputBuffer = ByteBuffer.allocateDirect(imageSize * imageSize * 3 * 4) // 4 bytes por float
        inputBuffer.order(ByteOrder.nativeOrder())
        inputBuffer.rewind()

        // Preenche o ByteBuffer com valores normalizados (0.0 a 1.0)
        for (y in 0 until imageSize) {
            for (x in 0 until imageSize) {
                val pixel = scaledBitmap.getPixel(x, y)
                // Extrai os canais RGB e normaliza os valores
                val r = (pixel shr 16 and 0xFF) / 255.0f
                val g = (pixel shr 8 and 0xFF) / 255.0f
                val b = (pixel and 0xFF) / 255.0f
                // Adiciona os valores normalizados ao buffer
                inputBuffer.putFloat(r)
                inputBuffer.putFloat(g)
                inputBuffer.putFloat(b)
            }
        }

        return inputBuffer
    }




    // Função para processar os resultados da detecção
    private fun parseDetectionResults(outputArray: Array<FloatArray>): List<DetectionResult> {
        val results = mutableListOf<DetectionResult>()

        // Suponha que outputArray[0] contém as pontuações para cada categoria
        for (i in 0 until outputArray[0].size) { // Alterar para outputArray[0].size
            val confidence = outputArray[0][i]
            if (confidence > 0.95) { // Ajuste o limiar de confiança conforme necessário

                if (i == 0){
                    results.add(DetectionResult("Maça,chance: ", confidence))
                }
                else if(i==1){
                    results.add(DetectionResult("Cereja,chance: ", confidence))
                }
                else if(i==2){
                    results.add(DetectionResult("Banana,chance: ", confidence))
                }


            }
        }

        return results
    }

}


// Classe de resultado de detecção
data class DetectionResult(val label: String, val confidence: Float)


