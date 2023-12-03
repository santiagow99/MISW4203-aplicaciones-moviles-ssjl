package com.example.vinyls_jetpack_application.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.repositories.AddFavoriteArtistRepository
import com.example.vinyls_jetpack_application.repositories.ArtistRepository

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)
    private val addFavoriteArtistRepository = AddFavoriteArtistRepository(application)
    private val _selectedArtist = MutableLiveData<Artist>()
    private val _artistId = MutableLiveData<Int>()

    val selectedArtist: LiveData<Artist>
        get() = _selectedArtist

    val artistId: LiveData<Int>
        get() = _artistId

    fun setSelectedArtist(artist: Artist) {
        _selectedArtist.value = artist
    }

    fun setArtistId(artistId: Int) {
        Log.d("view model artist 2", artistId.toString())
        _artistId.value = artistId
    }

    fun getArtistById(artistId: Int) {
        artistRepository.getArtistById(artistId,
            { artist ->
                Log.d("view model artist", artist.albums.toString())
                _selectedArtist.postValue(artist)
            },
            {
            }
        )
    }

    fun updateFavorite(collectorId: Int, artistId: Int) {
        addFavoriteArtistRepository.addFavorite(collectorId, artistId, {}, {})
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
