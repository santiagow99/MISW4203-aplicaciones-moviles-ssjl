package com.example.vinyls_jetpack_application.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.ArtistItemBinding
import com.example.vinyls_jetpack_application.models.Artist

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    var artists: List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick: ((Artist) -> Unit)? = null

    @LayoutRes
    private val artistItemLayout = R.layout.artist_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            artistItemLayout,
            parent,
            false
        )
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    inner class ArtistViewHolder(private val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(artist: Artist) {
            viewDataBinding.artistDetail = artist

            // Load artist cover image using Glide
            Glide.with(viewDataBinding.artistImageView.context)
                .load(artist.image)
                .into(viewDataBinding.artistImageView)

            // Set click listener on the entire card
            viewDataBinding.cardView.setOnClickListener {
                onItemClick?.invoke(artist)
            }

            // Set click listener on the image
            viewDataBinding.artistImageView.setOnClickListener {
                onItemClick?.invoke(artist)
            }
        }
    }
}
