package com.example.vinyls_jetpack_application.ui

import com.example.vinyls_jetpack_application.viewmodels.ArtistDetailViewModel
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.ArtistDetailFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArtistDetailFragment : Fragment() {

    private lateinit var viewModel: ArtistDetailViewModel
    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ArtistDetailViewModel.Factory(requireActivity().application)
        )[ArtistDetailViewModel::class.java]

        val args = ArtistDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getArtistById(args.artistId)

        viewModel.selectedArtist.observe(viewLifecycleOwner) { artist ->
            binding.artistDetailNameTextView.text = artist.name
            Glide.with(requireContext())
                .load(artist.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.artistDetailImageView)

            val descriptionTextView = binding.artistDetailDescriptionTextView
            val formattedDescriptionText = SpannableString("Descripción: ${artist.description}")
            formattedDescriptionText.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                12,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            descriptionTextView.text = formattedDescriptionText

            val birthDateTextView = binding.artistDetailBirthdateTextView
            val formattedbirthDateText = SpannableString("Fecha de nacimiento: ${formatBirthDate(artist.birthDate)}")
            formattedbirthDateText.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                20,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            birthDateTextView.text = formattedbirthDateText
        }
    }

    private fun formatBirthDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date as Date)
        } catch (e: ParseException) {
            e.printStackTrace()
            inputDate
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val navController = findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.artistDetailFragment) {
                activity.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
            } else {
                activity.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}