package com.example.vinyls_jetpack_application.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.repositories.ArtistRepository

class ArtistViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    private val _artists = MutableLiveData<List<Artist>>()

    val artists: LiveData<List<Artist>>
        get() = _artists

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val originalArtists = mutableListOf<Artist>()

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        artistRepository.refreshData({
            originalArtists.clear()
            originalArtists.addAll(it)
            _artists.postValue(it)
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
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun filterArtistsByName(query: String) {
        if (query.isBlank()) {
            _artists.postValue(originalArtists)
        } else {
            val filteredArtists = originalArtists.filter { it.name.contains(query, ignoreCase = true) }
            _artists.postValue(filteredArtists)
        }
    }
}
