package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.ArtistItemBinding
import com.example.vinyls_jetpack_application.viewmodels.AlbumDetailViewModel
import com.example.vinyls_jetpack_application.viewmodels.ArtistDetailViewModel

class ArtistDetailActivity : AppCompatActivity() {

    private var artistId: Int = 0
    private lateinit var navController: NavController
    private var viewModel: ArtistDetailViewModel = ArtistDetailViewModel(application)

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
