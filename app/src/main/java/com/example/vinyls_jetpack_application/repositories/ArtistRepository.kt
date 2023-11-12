package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class ArtistRepository(private val application: Application) {
    fun refreshData(callback: (List<Artist>) -> Unit, onError: (VolleyError) -> Unit) {
        // Determine the data source to be used. If necessary, query the network by executing the following code
        NetworkServiceAdapter.getInstance(application).getArtists({
            // Save the artists from the variable 'it' into a local data store for future use
            callback(it)
        }, onError)
    }

    fun getArtistById(artistId: Int, callback: (Artist) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtist(artistId, { callback(it) }, onError)
    }
}
