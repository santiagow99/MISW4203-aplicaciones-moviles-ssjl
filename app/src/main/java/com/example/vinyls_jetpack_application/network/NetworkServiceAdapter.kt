package com.example.vinyls_jetpack_application.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.models.AlbumDetail
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.models.Collector
import com.example.vinyls_jetpack_application.models.Track
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vynils-back-heroku.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        val gson = Gson()
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    Log.e("item album -->", item.toString())
                    list.add(i, Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                    ))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }
    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("collectors",
            { response ->
                Log.d("tagb", response)
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                onComplete(list)
            },
            {
                onError(it)
                Log.d("", it.message.toString())
            }))
    }

    fun getAlbum(albumId:Int, onComplete:(resp: AlbumDetail)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val tracks = resp.getJSONArray("tracks")
                val tracksList = mutableListOf<Track>()
                for (i in 0 until tracks.length()) {
                    val item = tracks.getJSONObject(i)
                    tracksList.add(
                        Track(i,
                            name = item.getString("name"),
                            duration = item.getString("duration")
                        )
                    )
                }
                val album=AlbumDetail(
                    albumId = resp.getInt("id"),
                    name = resp.getString("name"),
                    cover = resp.getString("cover"),
                    recordLabel = resp.getString("recordLabel"),
                    releaseDate = resp.getString("releaseDate"),
                    genre = resp.getString("genre"),
                    description = resp.getString("description"),
                    tracks = tracksList
                )
                onComplete(album)
            },
            {
                onError(it)
            }))

    }

    fun getArtists(
        onComplete: (resp: List<Artist>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest(
                "musicians",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                birthDate = item.getString("birthDate"),
                                albums = emptyList(),
                                performerPrizes = emptyList()
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                })
        )
    }

    fun getArtist(
        artistId: Int,
        onComplete: (resp: Artist) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest(
                "musicians/$artistId",
                Response.Listener<String> { response ->
                    val resp = JSONObject(response)
                    val artist = Artist(
                        artistId = resp.getInt("id"),
                        name = resp.getString("name"),
                        image = resp.getString("image"),
                        description = resp.getString("description"),
                        birthDate = resp.getString("birthDate"),
                        albums = emptyList(),
                        performerPrizes = emptyList()
                    )
                    onComplete(artist)
                },
                {
                    onError(it)
                })
        )
    }

    fun addAlbumToMusician(
        artistId: Int?,
        albumId: Int?,
        onComplete: (resp: String) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            postRequest(
                "musicians/$artistId/albums/$albumId",
                JSONObject(),
                Response.Listener<JSONObject> { response ->
                    Log.d("Response Add Album",response.toString())
                    onComplete(response.toString())
                }
            ) {
                onError(it)
            }
        )
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}