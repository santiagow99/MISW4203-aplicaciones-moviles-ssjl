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
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    private val cacheManager = CacheManager.getInstance(context)

    companion object {
        const val BASE_URL = "https://vynils-back-heroku.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        cacheManager.getCachedAlbums("albums")?.let { cachedAlbums ->
            onComplete(cachedAlbums)
            return
        }

        requestQueue.add(
            getRequest(
                "albums",
                { response ->
                    val list = parseAlbumsResponse(response)
                    cacheManager.setCachedAlbums("albums", list)
                    onComplete(list)
                },
                { onError(it) })
        )
    }

    fun getCollectors(onComplete: (resp: List<Collector>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest(
                "collectors",
                { response ->
                    val list = parseCollectorsResponse(response)
                    onComplete(list)
                },
                {
                    onError(it)
                    Log.d("", it.message.toString())
                })
        )
    }

    fun getAlbum(albumId:Int, onComplete:(resp: AlbumDetail)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
                val resp = JSONObject(response)
                val tracks = resp.getJSONArray("tracks")
                val tracksList = mutableListOf<Track>()
                var item:JSONObject?
                for (i in 0 until tracks.length()) {
                    item = tracks.getJSONObject(i)
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
        cacheManager.getCachedArtists("artists")?.let { cachedArtists ->
            onComplete(cachedArtists)
            return
        }
        requestQueue.add(
            getRequest(
                "musicians",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    var item:JSONObject?
                    for (i in 0 until resp.length()) {
                        item = resp.getJSONObject(i)
                        val albums = mutableListOf<Album>()
                        val albumsList = item.getJSONArray("albums")
                        for (i in 0 until albumsList.length()) {
                            val albumObject = albumsList.getJSONObject(i)
                            albums.add(
                                Album(
                                    albumId = albumObject.getInt("id"),
                                    name = albumObject.getString("name"),
                                    cover = albumObject.getString("cover"),
                                    releaseDate = albumObject.getString("releaseDate"),
                                    description = albumObject.getString("description"),
                                    genre = albumObject.getString("genre"),
                                    recordLabel = albumObject.getString("recordLabel")
                                )
                            )
                        }
                        list.add(
                            Artist(
                                artistId = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                birthDate = item.getString("birthDate"),
                                albums = albums,
                                performerPrizes = emptyList()
                            )
                        )
                    }
                    cacheManager.setCachedArtists("artists", list)
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
        Log.d("Artists", artistId.toString())
        val cachedArtists = cacheManager.getCachedArtists("artists")
        cachedArtists?.find {
            it.artistId == artistId
        }?.let{ cachedArtist ->
            Log.d("Cache", "Manager")
            onComplete(cachedArtist)
            return
        }
        requestQueue.add(
            getRequest(
                "musicians/$artistId",
                { response ->
                    val resp = JSONObject(response)
                    Log.d("Artists", resp.toString())
                    val list = mutableListOf<Album>()
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

    fun addAlbum(
        album: Album,
        onComplete: (resp: String) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        val requestBody = JSONObject().apply {
            put("name", album.name)
            put("cover", album.cover)
            put("releaseDate", album.releaseDate)
            put("description", album.description)
            put("genre", album.genre)
            put("recordLabel", album.recordLabel)
        }

        requestQueue.add(
            postRequest(
                "albums",
                requestBody,
                Response.Listener { response ->
                    Log.d("addAlbum", "Response received: $response")
                    onComplete(response.toString())
                },
                { error ->
                    Log.e("addAlbum", "Error in adding album: ${error.message}", error)
                    onError(error)
                }
            )
        )
    }
    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    internal fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        Log.d("postRequest", "Sending POST request to $path with body: $body")
        return JsonObjectRequest(Request.Method.POST, BASE_URL + path, body,
            Response.Listener { response ->
                Log.d("postRequest", "Response received: $response")
                responseListener.onResponse(response)
            },
            { error ->
                Log.e("postRequest", "Error in POST request: ${error.message}", error)
                errorListener.onErrorResponse(error)
            }
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(Request.Method.PUT, BASE_URL + path, body, responseListener, errorListener)
    }

    private fun parseAlbumsResponse(response: String): List<Album> {
        val resp = JSONArray(response)
        return List(resp.length()) { i ->
            val item = resp.getJSONObject(i)
            Log.e("item album -->", item.toString())
            Album(
                albumId = item.getInt("id"),
                name = item.getString("name"),
                cover = item.getString("cover"),
                recordLabel = item.getString("recordLabel"),
                releaseDate = item.getString("releaseDate"),
                genre = item.getString("genre"),
                description = item.getString("description")
            )
        }
    }

    private fun parseCollectorsResponse(response: String): List<Collector> {
        val resp = JSONArray(response)
        return List(resp.length()) { i ->
            val item = resp.getJSONObject(i)
            Collector(
                collectorId = item.getInt("id"),
                name = item.getString("name"),
                telephone = item.getString("telephone"),
                email = item.getString("email")
            )
        }
    }
}
