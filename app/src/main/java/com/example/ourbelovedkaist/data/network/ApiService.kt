package com.example.ourbelovedkaist.data.network

import com.example.ourbelovedkaist.data.model.CapsuleRequest
import com.example.ourbelovedkaist.data.model.CapsuleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("capsules") // "capsules" 엔드포인트 확인
    fun createCapsule(@Body request: CapsuleRequest): Call<CapsuleResponse>
}
