package com.example.ourbelovedkaist

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/capsules")
    fun createCapsule(@Body capsule: CapsuleRequest): Call<CapsuleResponse>
}
