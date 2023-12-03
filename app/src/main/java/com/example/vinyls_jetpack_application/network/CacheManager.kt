    package com.example.vinyls_jetpack_application.network
    import android.content.Context
    import com.example.vinyls_jetpack_application.models.Album
    import com.example.vinyls_jetpack_application.models.Artist
    import com.example.vinyls_jetpack_application.models.Comment


    class CacheManager(context: Context) {
        companion object{
            private var instance: CacheManager? = null
            fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: CacheManager(context).also {
                        instance = it
                    }
                }
        }

        private val albumCache = HashMap<String, List<Album>>()
        private val artistCache = HashMap<String, List<Artist>>()
        private val commentCache = HashMap<String, List<Comment>>()

        fun getCachedAlbums(key: String): List<Album>? {
            return albumCache[key]
        }

        fun setCachedAlbums(key: String, albums: List<Album>) {
            albumCache[key] = albums
        }

        fun getCachedArtists(key: String): List<Artist>? {
            return artistCache[key]
        }

        fun setCachedArtists(key: String, artists: List<Artist>) {
            artistCache[key] = artists
        }

        fun getCachedComments(key: String): List<Comment>? {
            return commentCache[key]
        }

        fun setCachedComments(key: String, comments: List<Comment>) {
            commentCache[key] = comments
        }
    }