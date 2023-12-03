package com.example.vinyls_jetpack_application.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class AddFavoriteArtistRepository(private val application: Application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun addFavorite(
        collector: Int,
        artist: Int,
        onComplete: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.toggleFavoriteAlbum(
            collector,
            artist,
            { onComplete.invoke() },
            onError
        )
    }
}
