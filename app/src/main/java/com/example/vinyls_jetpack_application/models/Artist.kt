package com.example.vinyls_jetpack_application.models

data class Artist(
    val artistId: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album>,
    val performerPrizes: List<Prize>
)
