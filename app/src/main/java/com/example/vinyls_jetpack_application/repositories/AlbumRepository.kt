package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.models.AlbumDetail
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class AlbumRepository (private val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
       NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }

    fun getAlbumById(albumId: Int, callback: (AlbumDetail) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId, {callback(it)}, onError)
    }
}