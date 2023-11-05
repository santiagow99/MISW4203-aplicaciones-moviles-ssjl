package com.example.vinyls_jetpack_application.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vinyls_jetpack_application.databinding.AlbumDetailItemBinding
import com.example.vinyls_jetpack_application.R

class AlbumDetailActivity : AppCompatActivity() {

    private var albumId: Int? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AlbumDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        albumId = intent.extras?.getInt("albumId")


    }

    fun getAlbumId(): Int? {
        return this.albumId
    }

}