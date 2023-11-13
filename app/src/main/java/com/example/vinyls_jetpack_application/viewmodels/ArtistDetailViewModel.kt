import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.repositories.ArtistRepository

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val artistRepository = ArtistRepository(application)

    private val _selectedArtist = MutableLiveData<Artist>()

    val selectedArtist: LiveData<Artist>
        get() = _selectedArtist

    fun setSelectedArtist(artist: Artist) {
        _selectedArtist.value = artist
    }

    fun getArtistById(artistId: Int) {
        artistRepository.getArtistById(artistId,
            { artist ->
                _selectedArtist.postValue(artist)
            },
            { error ->
            }
        )
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
