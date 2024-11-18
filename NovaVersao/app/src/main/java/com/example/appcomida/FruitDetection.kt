package com.example.appcomida

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class FruitDetection(private val context: Context) {


    
    fun loadModel(): Interpreter {
        val assetManager = context.assets
        val modelDescriptor = assetManager.openFd("model_unquant.tflite")
        val inputStream = modelDescriptor.createInputStream()
        val mappedByteBuffer = inputStream.use {
            it.channel.map(FileChannel.MapMode.READ_ONLY, modelDescriptor.startOffset, modelDescriptor.declaredLength)
        }


        return Interpreter(mappedByteBuffer)
    }


    fun detectFruit(bitmap: Bitmap, interpreter: Interpreter): List<DetectionResult> {
        val imageSize = 224
        val channels = 3

        // Pré-processar a imagem
        val inputBuffer: ByteBuffer = preprocessImage(bitmap, imageSize)


        val outputArray = Array(1) { FloatArray(3) }


        interpreter.run(inputBuffer, outputArray)


        return parseDetectionResults(outputArray)
    }

    // Função para pré-processar a imagem
    fun preprocessImage(bitmap: Bitmap, imageSize: Int): ByteBuffer {

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)


        val inputBuffer = ByteBuffer.allocateDirect(imageSize * imageSize * 3 * 4) // 4 bytes por float
        inputBuffer.order(ByteOrder.nativeOrder())
        inputBuffer.rewind()


        for (y in 0 until imageSize) {
            for (x in 0 until imageSize) {
                val pixel = scaledBitmap.getPixel(x, y)

                val r = (pixel shr 16 and 0xFF) / 255.0f
                val g = (pixel shr 8 and 0xFF) / 255.0f
                val b = (pixel and 0xFF) / 255.0f

                inputBuffer.putFloat(r)
                inputBuffer.putFloat(g)
                inputBuffer.putFloat(b)
            }
        }

        return inputBuffer
    }





    private fun parseDetectionResults(outputArray: Array<FloatArray>): List<DetectionResult> {
        val results = mutableListOf<DetectionResult>()


        for (i in 0 until outputArray[0].size) {
            val confidence = outputArray[0][i]
            if (confidence > 0.95) {

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



data class DetectionResult(val label: String, val confidence: Float)

