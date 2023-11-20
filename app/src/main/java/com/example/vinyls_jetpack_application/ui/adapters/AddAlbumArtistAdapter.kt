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
import com.example.vinyls_jetpack_application.viewmodels.AddAlbumArtistViewModel

class AddAlbumArtistAdapter() : RecyclerView.Adapter<AddAlbumArtistAdapter.AddAlbumArtistViewHolder>() {


    var viewModel: AddAlbumArtistViewModel? = null

    var albumId: Int? = null;

    var albums: List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class AddAlbumArtistViewHolder(val viewDataBinding: AlbumItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root) {
            companion object {
                @LayoutRes
                val LAYOUT = R.layout.album_item
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAlbumArtistViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AddAlbumArtistViewHolder.LAYOUT,
            parent,
            false
        )
        return AddAlbumArtistViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AddAlbumArtistAdapter.AddAlbumArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            var album = albums[position].albumId
            // Load album cover image using Glide
            Glide.with(holder.viewDataBinding.albumCoverImageView.context)
                .load(albums[position].cover)
                .into(holder.viewDataBinding.albumCoverImageView)
            holder.viewDataBinding.albumCoverImageView.setOnClickListener {
                this.albumId = album
                holder.viewDataBinding.cardView.strokeColor = 0x41FFCC
            }
        }
    }

}