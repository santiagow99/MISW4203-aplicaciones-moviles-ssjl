package com.example.vinyls_jetpack_application.ui

import ArtistDetailViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls_jetpack_application.databinding.ArtistDetailFragmentBinding

class ArtistDetailFragment : Fragment() {

    private lateinit var viewModel: ArtistDetailViewModel
    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ArtistDetailViewModel.Factory(requireActivity().application)
        )[ArtistDetailViewModel::class.java]

        val args = ArtistDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getArtistById(args.artistId)

        viewModel.selectedArtist.observe(viewLifecycleOwner) { artist ->
            // Update UI with artist details
            binding.artistDetailNameTextView.text = artist.name
            // Set other details as needed
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}