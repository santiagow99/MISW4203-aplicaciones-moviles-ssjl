package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vinyls_jetpack_application.databinding.ArtistItemBinding
import com.example.vinyls_jetpack_application.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
