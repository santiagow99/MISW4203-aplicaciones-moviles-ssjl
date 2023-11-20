package com.example.vinyls_jetpack_application.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.AlbumItemBinding
import com.example.vinyls_jetpack_application.models.Album

class ArtistDetailAdapter : RecyclerView.Adapter<ArtistDetailAdapter.ArtistDetailViewHolder>() {

    var albums: List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ArtistDetailViewHolder(val viewDataBinding: AlbumItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistDetailViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistDetailViewHolder.LAYOUT,
            parent,
            false
        )
        return ArtistDetailViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ArtistDetailViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            var album = albums[position].albumId
            // Load album cover image using Glide
            Glide.with(holder.viewDataBinding.albumCoverImageView.context)
                .load(albums[position].cover)
                .into(holder.viewDataBinding.albumCoverImageView)
            holder.viewDataBinding.albumCoverImageView.setOnClickListener {
            }
        }
    }
}