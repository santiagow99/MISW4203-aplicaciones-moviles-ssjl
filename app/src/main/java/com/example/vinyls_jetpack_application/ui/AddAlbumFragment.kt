package com.example.vinyls_jetpack_application.ui

import AddAlbumViewModel
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vinyls_jetpack_application.databinding.AddAlbumFragmentBinding
import com.example.vinyls_jetpack_application.models.Album
import java.util.Calendar

class AddAlbumFragment : Fragment() {
    private val selectedCalendar = Calendar.getInstance()
    private lateinit var viewModel: AddAlbumViewModel
    private var _binding: AddAlbumFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameEditText: EditText
    private lateinit var coverEditText: EditText
    private lateinit var editTextReleaseDate: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var spinnerGenre: Spinner
    private lateinit var spinnerRecordLabel: Spinner
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddAlbumFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AddAlbumViewModel::class.java)

        nameEditText = binding.editTextName
        coverEditText = binding.editTextCover
        editTextReleaseDate = binding.editTextReleaseDate
        descriptionEditText = binding.editTextDescription
        spinnerGenre = binding.editTextGenre
        spinnerRecordLabel = binding.editTextRecordLabel
        saveButton = binding.saveButton

        val calendar = Calendar.getInstance()

        editTextReleaseDate.setOnClickListener {
            showDatePicker()
        }
        saveButton.setOnClickListener {
            Log.d("SaveButton", "Save button clicked")
            saveAlbum()
        }

         val genreOptions = arrayOf("Classical", "Salsa", "Rock", "Folk")
        val recordLabelOptions = arrayOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")

        val genreAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genreOptions)
        val recordLabelAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, recordLabelOptions)

        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        recordLabelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGenre.adapter = genreAdapter
        spinnerRecordLabel.adapter = recordLabelAdapter

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
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.let { button ->
            button.setBackgroundColor(Color.parseColor("#FF9800"))
        }
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.let { button ->
            button.setBackgroundColor(Color.parseColor("#FF9800"))
        }
    }

    private fun saveAlbum() {
        val albumId = viewModel.generateAlbumId()
        val name = nameEditText.text.toString()
        val cover = coverEditText.text.toString()
        val releaseDate = editTextReleaseDate.text.toString()
        val description = descriptionEditText.text.toString()
        val genre = spinnerGenre.selectedItem.toString()
        val recordLabel = spinnerRecordLabel.selectedItem.toString()

        if (name.isBlank() || cover.isBlank() || releaseDate.isBlank() || description.isBlank() || genre.isBlank() || recordLabel.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Todos los campos son obligatorios",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

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
                val action =
                    AddAlbumFragmentDirections.actionAddAlbumFragmentToAlbumListFragment()
                findNavController().navigate(action)

                viewModel.getUpdatedAlbums(
                    onComplete = { updatedAlbums ->
                    },
                    onError = { volleyError ->
                    }
                )
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
