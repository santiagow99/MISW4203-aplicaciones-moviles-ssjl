package com.example.vinyls_jetpack_application.repositories
import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class AddAlbumRepository(private val application: Application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun addAlbum(
        album: Album,
        onComplete: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.addAlbum(
            album,
            { onComplete.invoke() },
            onError
        )
    }
}
