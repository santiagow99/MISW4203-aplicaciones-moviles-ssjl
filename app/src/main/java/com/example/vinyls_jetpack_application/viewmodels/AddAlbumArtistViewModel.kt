package com.example.vinyls_jetpack_application.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import com.example.vinyls_jetpack_application.repositories.AddAlbumToArtistRepository
import com.example.vinyls_jetpack_application.repositories.AlbumRepository

class AddAlbumArtistViewModel(application: Application): AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)
    private val addAlbumToArtistRepository = AddAlbumToArtistRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    private val _albumSelected = MutableLiveData<Album>()

    val albums: LiveData<List<Album>>
        get() = _albums

    val albumSelected: LiveData<Album>
        get() = _albumSelected

    fun setSelectedAlbum(album: Album) {
        _albumSelected.value = album
    }

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val originalAlbums = mutableListOf<Album>()

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumsRepository.refreshData({
            originalAlbums.clear()
            originalAlbums.addAll(it)
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun filterAlbumsByName(query: String) {
        if (query.isBlank()) {
            _albums.postValue(originalAlbums)
        } else {
            val filteredAlbums = originalAlbums.filter { it.name.contains(query, ignoreCase = true) }
            _albums.postValue(filteredAlbums)
        }
    }
}