package com.example.appcomida

import com.example.appcomida.dataclass.alimento
import com.example.appcomida.dataclass.compra
import com.example.appcomida.dataclass.user
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.sql.Date

interface ApiService {

    @GET("cliente/{email}/{senha}")
    suspend fun login(@Path("email") email: String, @Path("senha") password: String): Response<String>

    @POST("cliente")
    suspend fun register(@Body user: user): Response<user>

    @POST("compras")
    suspend fun registroCompras(@Body compra: compra): Response<compra>

    @GET("compras")
    suspend fun listagemCompras(): Response<List<compra>>

    @POST("alimentos")
    suspend fun registroAlimentos(@Body alimento: alimento): Response<alimento>

    @GET("/alimentos")
    fun getAllAlimentos(): Call<List<alimento>>

    @GET("alimentos/{date}")
    suspend fun data(@Path("date") date: CalendarDay): Response<List<alimento>>
}