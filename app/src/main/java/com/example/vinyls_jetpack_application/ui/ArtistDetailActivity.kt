package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.vinyls_jetpack_application.databinding.ArtistItemBinding

class ArtistDetailActivity : AppCompatActivity() {

    private var artistId: Int = 0
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ArtistItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        artistId = intent.extras?.getInt("artistId") ?: 0


    }

    fun getArtistId(): Int {
        return artistId
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
