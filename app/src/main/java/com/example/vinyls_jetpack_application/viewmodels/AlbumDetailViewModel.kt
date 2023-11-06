package com.example.vinyls_jetpack_application.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinyls_jetpack_application.models.Album
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.AlbumDetail
import com.example.vinyls_jetpack_application.repositories.AlbumRepository
class AlbumDetailViewModel(application: Application, albumId: Int) : AndroidViewModel(application){

    private val albumsRepository = AlbumRepository(application)
    private val _albumDetails = MutableLiveData<AlbumDetail>()
    val albumDetails: LiveData<AlbumDetail>
        get() = _albumDetails
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    init {
        loadAlbumDetails(albumId)
    }
    private fun loadAlbumDetails(albumId: Int) {

        albumsRepository.getAlbumById(albumId, { album: AlbumDetail ->
            _albumDetails.postValue(album)
        }, { error: VolleyError ->
            error
        })
    }
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
    class Factory(val app: Application, private var idAlbum: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app, idAlbum) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}