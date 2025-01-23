package com.example.ourbelovedkaist.data.network

import com.example.ourbelovedkaist.data.model.CapsuleRequest
import com.example.ourbelovedkaist.data.model.CapsuleResponse
import com.example.ourbelovedkaist.data.model.MemoryRequest
import com.example.ourbelovedkaist.data.model.MemoryResponse
import com.example.ourbelovedkaist.data.model.Memory
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("capsules") // "capsules" 엔드포인트 확인
    fun createCapsule(@Body request: CapsuleRequest): Call<CapsuleResponse>

    @POST("capsules/{capsuleId}/memories")
    fun createMemory(
        @Path("capsuleId") capsuleId: String,
        @Body memoryRequest: MemoryRequest
    ): Call<MemoryResponse>

    @GET("capsules/{capsuleId}/memories")
    fun getMemories(@Path("capsuleId") capsuleId: String): Call<List<Memory>>
}
