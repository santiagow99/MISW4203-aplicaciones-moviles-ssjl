package com.example.vinyls_jetpack_application.ui

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.databinding.AddAlbumArtistFragmentBinding
import com.example.vinyls_jetpack_application.viewmodels.AlbumViewModel

class AddAlbumArtistFragment: Fragment() {
    private var _binding: AddAlbumArtistFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
}