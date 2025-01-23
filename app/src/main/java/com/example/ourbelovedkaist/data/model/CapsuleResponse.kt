package com.example.ourbelovedkaist.data.model

data class CapsuleResponse(
    val id: String,
    val name: String,
    val creator: String,
    val createdAt: String,
    val openDate: String?,
    val sealed: Boolean,
    val memoryCount: Int,
    val createdAtAsDateTime: String,
    val openDateAsDateTime: String?
)
