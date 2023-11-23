import android.app.Application
import androidx.lifecycle.*
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.repositories.AlbumRepository
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import org.json.JSONObject

class AlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)
    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    private val _albums = MutableLiveData<List<Album>>()

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val originalAlbums = mutableListOf<Album>()

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        albumsRepository.refreshData({
            originalAlbums.clear()
            originalAlbums.addAll(it)
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun filterAlbumsByName(query: String) {
        if (query.isBlank()) {
            _albums.postValue(originalAlbums)
        } else {
            val filteredAlbums = originalAlbums.filter { it.name.contains(query, ignoreCase = true) }
            _albums.postValue(filteredAlbums)
        }
    }

    fun saveAlbum(album: Album, onComplete: () -> Unit, onError: (VolleyError) -> Unit) {
        networkServiceAdapter.postRequest(
            "albums",
            albumToJsonObject(album),
            { response ->
                onComplete.invoke()
            },
            { error ->
                onError.invoke(error)
            }
        )
    }

    private fun albumToJsonObject(album: Album): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("albumId", album.albumId)
        jsonObject.put("name", album.name)
        jsonObject.put("cover", album.cover)
        jsonObject.put("releaseDate", album.releaseDate)
        jsonObject.put("description", album.description)
        jsonObject.put("genre", album.genre)
        jsonObject.put("recordLabel", album.recordLabel)
        return jsonObject
    }
}
