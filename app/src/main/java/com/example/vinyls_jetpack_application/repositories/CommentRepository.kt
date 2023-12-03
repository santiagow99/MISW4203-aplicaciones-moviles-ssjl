import android.app.Application
import com.android.volley.VolleyError
import com.example.vinyls_jetpack_application.models.Comment
import com.example.vinyls_jetpack_application.network.NetworkServiceAdapter

class CommentRepository(private val application: Application) {

    private val networkServiceAdapter = NetworkServiceAdapter.getInstance(application)

    fun getCommentsForAlbum(
        albumId: Int,
        onComplete: (List<Comment>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.getCommentsForAlbum(
            albumId,
            onComplete,
            onError
        )
    }


    fun addCommentToAlbum(
        albumId: Int,
        comment: Comment,
        onComplete: () -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        networkServiceAdapter.addCommentToAlbum(
            albumId,
            comment,
            { onComplete.invoke() },
            onError
        )
    }
}
