package com.example.ourbelovedkaist

data class Location(
    val id: String = "",  // Firestore 문서 ID
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = ""
)