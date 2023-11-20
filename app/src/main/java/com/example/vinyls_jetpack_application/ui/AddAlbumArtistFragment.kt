package com.example.vinyls_jetpack_application.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.AddAlbumArtistFragmentBinding
import com.example.vinyls_jetpack_application.ui.adapters.AddAlbumArtistAdapter
import com.example.vinyls_jetpack_application.viewmodels.AddAlbumArtistViewModel

class AddAlbumArtistFragment: Fragment() {
    private var _binding: AddAlbumArtistFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AddAlbumArtistViewModel
    private var viewModelAdapter: AddAlbumArtistAdapter? = null
    private lateinit var searchEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddAlbumArtistFragmentBinding.inflate(inflater, container, false)
        val bundle = arguments
        val artistId = bundle?.getInt("artistId")
        val view = binding.root
        viewModelAdapter = AddAlbumArtistAdapter()
        val button: Button = binding.root.findViewById(R.id.button_add_album)
        button.setOnClickListener{
            viewModel.addAlbumToArtist(viewModelAdapter!!.albumId, artistId!!)
            val toast = Toast.makeText(activity, "Asociado Correctamente41FFCC", Toast.LENGTH_LONG)
            toast.view?.setBackgroundColor(Color.parseColor("#28a745"))
            toast.show()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        Log.d("arguments", arguments.toString())
        val artistId = bundle?.getInt("artistId")
        recyclerView = view.findViewById(R.id.albumCarouselRv)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = viewModelAdapter

        searchEditText = view.findViewById(R.id.searchEditText)
        setupSearchListener()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val bundle = arguments
        Log.d("onActivityCreated", arguments.toString())
        val artistId = bundle?.getInt("artistId")
        Log.d("artistId", artistId.toString())
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModel = ViewModelProvider(this, AddAlbumArtistViewModel.Factory(activity.application))[AddAlbumArtistViewModel::class.java]
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            viewModelAdapter?.albums = albums
        }
        viewModelAdapter?.viewModel = viewModel
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

    private fun setupSearchListener() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val query = editable.toString()
                viewModel.filterAlbumsByName(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

}