package com.example.vinyls_jetpack_application.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.models.AlbumDetail
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class AlbumRepository (private val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbums({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    fun getAlbumById(albumId: Int, callback: (AlbumDetail) -> Unit, onError: (VolleyError) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId, {callback(it)}, onError)
    }
}