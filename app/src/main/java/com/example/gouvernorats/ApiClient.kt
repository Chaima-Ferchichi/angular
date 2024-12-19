package com.example.gouvernorats

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.100.188:8088"
    private const val BASE_URLservice = "http://192.168.100.188:8083"

    private  val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }
    private  val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}