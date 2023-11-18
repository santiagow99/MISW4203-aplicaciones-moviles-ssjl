package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import org.json.JSONObject

class AddAlbumToArtistRepository (private val application: Application) {

    fun postAddAlbumToArtist(
        artistId: Int,
        albumId: Int,
        callback: (JSONObject) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        NetworkServiceAdapter.getInstance(application).addAlbumToMusician(
            artistId,
            albumId,
            {callback(it)},
            onError
        )
    }
}