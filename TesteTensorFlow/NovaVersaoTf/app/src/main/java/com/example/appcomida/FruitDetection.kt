package com.example.appcomida

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.channels.FileChannel


class FruitDetection(private val context: Context) {

    // Carrega o modelo TensorFlow Lite
    // Na classe FruitDetection
    fun loadModel(): Interpreter {
        val assetManager = context.assets
        val modelDescriptor = assetManager.openFd("model_unquant.tflite")
        val inputStream = modelDescriptor.createInputStream()
        val mappedByteBuffer = inputStream.use { it.channel.map(FileChannel.MapMode.READ_ONLY, modelDescriptor.startOffset, modelDescriptor.declaredLength) }

        // Inicializa o Interpretador do TensorFlow Lite
        return Interpreter(mappedByteBuffer)
    }


    // Detecta frutas na imagem usando o modelo TensorFlow Lite
    fun detectFruit(bitmap: Bitmap, interpreter: Interpreter): List<DetectionResult> {
        val imageSize = 300  // Suponha que o modelo espere uma imagem 300x300

        // Pré-processar a imagem
        val inputBuffer: ByteBuffer = Utils.preprocessImage(bitmap, imageSize)

        // Criar array de saída para armazenar os resultados da inferência
        val outputArray = Array(1) { FloatArray(10) }  // Exemplo: 10 categorias de frutas

        // Executar a inferência
        interpreter.run(inputBuffer, outputArray)

        // Processar os resultados
        return Utils.parseDetectionResults(outputArray)
    }
}
