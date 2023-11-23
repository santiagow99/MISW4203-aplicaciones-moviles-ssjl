import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.AddAlbumFragmentBinding
import com.example.vinyls_jetpack_application.models.Album
import kotlin.random.Random

class AddAlbumFragment : Fragment() {

    private lateinit var viewModel: AlbumViewModel

    private lateinit var nameEditText: EditText
    private lateinit var coverEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var genreEditText: EditText
    private lateinit var recordLabelEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: AddAlbumFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_album_fragment, container, false
        )

        viewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)

        nameEditText = binding.editTextName
        coverEditText = binding.editTextCover
        releaseDateEditText = binding.editTextReleaseDate
        descriptionEditText = binding.editTextDescription
        genreEditText = binding.editTextGenre
        recordLabelEditText = binding.editTextRecordLabel
        saveButton = binding.saveButton

        saveButton.setOnClickListener {
            saveAlbum()
        }

        return binding.root
    }

    private fun generateAlbumId(): Int {
        return Random.nextInt(1, Int.MAX_VALUE)
    }
    private fun saveAlbum() {
        val albumId = generateAlbumId()
        val name = nameEditText.text.toString()
        val cover = coverEditText.text.toString()
        val releaseDate = releaseDateEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val genre = genreEditText.text.toString()
        val recordLabel = recordLabelEditText.text.toString()

        val album = Album(
            albumId = albumId,
            name = name,
            cover = cover,
            recordLabel = recordLabel,
            releaseDate = releaseDate,
            genre = genre,
            description = description
        )

        viewModel.saveAlbum(album,
            onComplete = {
                Toast.makeText(requireContext(), "Álbum guardado exitosamente", Toast.LENGTH_SHORT).show()
            },
            onError = { volleyError ->
                Toast.makeText(requireContext(), "Error al guardar el álbum", Toast.LENGTH_SHORT).show()
                Log.e("AddAlbumFragment", "Error al guardar el álbum: ${volleyError.message}")
            }
        )
    }
}
