package com.example.loginfrag.http

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class HttpHelper {
    fun post (json:String): String {
        val URL = "http://192.168.15.10.1080/frutas"

        val headerHttp = MediaType.parse("application/json; charset=utf-8")

        val cliente = OkHttpClient()

        val body = RequestBody.create(headerHttp, json)

        var request = Request.Builder().url(URL).post(body).build()

        val response = cliente.newCall(request).execute()

        return response.body().toString()
    }
}