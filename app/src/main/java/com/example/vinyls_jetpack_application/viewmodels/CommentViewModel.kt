import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Comment
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter
import org.json.JSONObject
import kotlin.random.Random

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun generateCommentId(): Int {
        return Random.nextInt(1, Int.MAX_VALUE)
    }

    fun saveComment(
        albumId: Int,
        comment: Comment,
        onComplete: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.addCommentToAlbum(
            albumId,
            comment,
            onComplete = {
                onComplete.invoke()
            },
            onError = { error ->
                onError.invoke(error)
            }
        )
    }

    fun getCommentsForAlbum(
        albumId: Int,
        onComplete: (List<Comment>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.getCommentsForAlbum(
            albumId,
            onComplete = {
                onComplete(it)
            },
            onError = {
                onError(it)
            }
        )
    }

    private fun commentToJsonObject(comment: Comment): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("description", comment.description)
        jsonObject.put("rating", comment.rating)
        jsonObject.put("collector", JSONObject().apply {
            put("id", comment.collectorId)
        })
        return jsonObject
    }
}
