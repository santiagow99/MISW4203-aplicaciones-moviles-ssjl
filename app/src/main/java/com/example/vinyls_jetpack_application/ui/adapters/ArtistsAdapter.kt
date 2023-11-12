package com.example.vinyls_jetpack_application.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.ArtistItemBinding
import com.example.vinyls_jetpack_application.models.Artist
import com.example.vinyls_jetpack_application.ui.AlbumDetailActivity
import com.example.vinyls_jetpack_application.ui.ArtistDetailActivity

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {

    var artists: List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false
        )
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
            // Load artist cover image using Glide
            Glide.with(holder.viewDataBinding.artistImageView.context)
                .load(artists[position].image)
                .into(holder.viewDataBinding.artistImageView)
            holder.viewDataBinding.artistImageView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ArtistDetailActivity::class.java)
                val artist = artists[position]
                intent.putExtra("artistId", artist.artistId)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return artists.size
    }

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }
}
