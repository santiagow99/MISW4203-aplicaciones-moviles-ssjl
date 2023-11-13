package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class ArtistRepository(private val application: Application) {
    fun refreshData(callback: (List<Artist>) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists({
          callback(it)
        }, onError)
    }

    fun getArtistById(artistId: Int, callback: (Artist) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtist(artistId, { callback(it) }, onError)
    }
}
