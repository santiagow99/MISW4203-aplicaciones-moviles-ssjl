package com.example.vinyls_jetpack_application.models

data class Comment(
    val id: String,
    val description: String,
    val rating: Int,
    val collectorId: String
)