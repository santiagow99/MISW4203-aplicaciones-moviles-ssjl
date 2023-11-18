package com.example.vinyls_jetpack_application.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.ActivityAlbumDetailBinding

class AlbumDetailActivity : AppCompatActivity() {

    private var albumId: Int = 0
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate Activity", "0")
        super.onCreate(savedInstanceState)
        val binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        albumId = intent.extras?.getInt("albumId") ?: 0
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_album_detail_fragment) as NavHostFragment
        navController = navHostFragment.navController
        Log.d("act", navController.toString())

    }

    fun getAlbumId(): Int {
        return this.albumId
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}