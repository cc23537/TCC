package com.example.appcomida

import com.example.appcomida.dataclass.alimento
import com.example.appcomida.dataclass.compra
import com.example.appcomida.dataclass.user
import retrofit2.Response
import retrofit2.http.*


public interface ApiService {
    @GET("cliente/{email}/{senha}")
    fun login(@Path("email") email: String, @Path("senha") password: String): Response<String>

    @POST("cliente")
    fun register(@Body user: user): Response<user>

    @POST("compras")
    fun registroCompras(@Body compra: compra): Response<compra>
    @GET("compras")
    fun listagemCompras(): Response<List<compra>>

    @POST("alimentos")
    fun registroAlimentos(@Body alimento: alimento): Response<alimento>
    @GET("alimentos")
    fun listagemArmario(): Response<List<alimento>>
}