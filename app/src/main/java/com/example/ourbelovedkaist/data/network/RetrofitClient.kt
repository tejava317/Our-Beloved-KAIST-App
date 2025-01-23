package com.example.ourbelovedkaist.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.madcamp.store/api/v1/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // BASE_URL 설정
            .addConverterFactory(GsonConverterFactory.create()) // Gson 사용
            .build()
            .create(ApiService::class.java)
    }
}
