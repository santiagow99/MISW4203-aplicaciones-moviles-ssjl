package com.example.vinyls_jetpack_application.ui

import AddAlbumViewModel
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.AddAlbumFragmentBinding
import com.example.vinyls_jetpack_application.models.Album
import java.util.Calendar

class AddAlbumFragment : Fragment() {
    val selectedCalendar = Calendar.getInstance()
    private lateinit var viewModel: AddAlbumViewModel
    private var _binding: AddAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var nameEditText: EditText
    private lateinit var coverEditText: EditText
    private lateinit var editTextReleaseDate: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var genreEditText: EditText
    private lateinit var recordLabelEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddAlbumFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AddAlbumViewModel::class.java)

        nameEditText = binding.editTextName
        coverEditText = binding.editTextCover
        editTextReleaseDate = binding.editTextReleaseDate  // Cambié la asignación a editTextReleaseDate
        descriptionEditText = binding.editTextDescription
        genreEditText = binding.editTextGenre
        recordLabelEditText = binding.editTextRecordLabel
        saveButton = binding.saveButton

        val calendar = Calendar.getInstance()

        editTextReleaseDate.setOnClickListener {
            showDatePicker()
        }
        saveButton.setOnClickListener {
            saveAlbum()
        }

        return binding.root
    }

    private fun showDatePicker() {
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            selectedCalendar.set(y, m, d)
            editTextReleaseDate.setText("$y-$m-$d")
        }

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            listener,
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.GREEN)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.GREEN)
    }

    private fun saveAlbum() {
        val albumId = viewModel.generateAlbumId()
        val name = nameEditText.text.toString()
        val cover = coverEditText.text.toString()
        val releaseDate = editTextReleaseDate.text.toString()
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

        viewModel.saveAlbum(
            album,
            onComplete = {
                Toast.makeText(
                    requireContext(),
                    "Álbum guardado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onError = { volleyError ->
                Toast.makeText(
                    requireContext(),
                    "Error al guardar el álbum",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
