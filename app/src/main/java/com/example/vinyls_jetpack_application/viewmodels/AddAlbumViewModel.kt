import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import org.json.JSONObject
import kotlin.random.Random

class AddAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun generateAlbumId(): Int {
        return Random.nextInt(1, Int.MAX_VALUE)
    }

    fun saveAlbum(
        album: Album,
        onComplete: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.postRequest(
            "albums",
            albumToJsonObject(album),
            { response ->
                Log.d("SaveAlbum", "Server Response: $response")
                onComplete.invoke()
            },
            { error ->
                Log.e("SaveAlbum", "Error saving album: ${error.message}", error)
                onError.invoke(error)
            }
        )
    }

    private fun albumToJsonObject(album: Album): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name", album.name)
        jsonObject.put("cover", album.cover)
        jsonObject.put("releaseDate", album.releaseDate)
        jsonObject.put("description", album.description)
        jsonObject.put("genre", album.genre)
        jsonObject.put("recordLabel", album.recordLabel)
        Log.d("JsonObject", jsonObject.toString())
        return jsonObject
    }
}
