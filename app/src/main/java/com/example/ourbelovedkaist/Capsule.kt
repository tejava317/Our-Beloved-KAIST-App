package com.example.ourbelovedkaist

data class Capsule(
    val id: String,
    val name: String,
    val creator: String,
    val createdAt: String,
    val openDate: String?,
    val sealed: Boolean,
    val memoryCount: Int,
    val openDateAsDateTime: String?,
    val createdAtAsDateTime: String
)