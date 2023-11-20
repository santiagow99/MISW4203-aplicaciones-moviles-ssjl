package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.databinding.AlbumDetailItemBinding
import com.example.vinyls_jetpack_application.viewmodels.AlbumDetailViewModel
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.ui.adapters.AlbumDetailTrackAdapter

class AlbumDetailFragment: Fragment()  {
    private var _binding: AlbumDetailItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: AlbumDetailTrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("onCreateView -->","0")
        _binding = AlbumDetailItemBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumDetailTrackAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.tracksList)
        recyclerView.adapter = viewModelAdapter
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val albumId = (getActivity() as AlbumDetailActivity?)?.getAlbumId() ?: 0
        Log.d("onActivityCreated -->","0")
        viewModel =
            ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, albumId))[AlbumDetailViewModel::class.java]
        viewModel.albumDetails.observe(viewLifecycleOwner) { album ->
            Log.d("albumDetails -->", album.name)
            binding.albumDetail = album
            viewModelAdapter?.tracks = album.tracks
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}