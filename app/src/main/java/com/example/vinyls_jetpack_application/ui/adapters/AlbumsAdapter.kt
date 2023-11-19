package com.example.vinyls_jetpack_application.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.AlbumItemBinding
import com.example.vinyls_jetpack_application.models.Album
import com.example.vinyls_jetpack_application.ui.AlbumDetailActivity

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    var albums: List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: AlbumItemBinding = DataBindingUtil.inflate(
            inflater,
            AlbumViewHolder.LAYOUT,
            parent,
            false
        )
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }

        fun bind(album: Album) {
            viewDataBinding.album = album

            // Load album cover image using Glide
            Glide.with(viewDataBinding.albumCoverImageView.context)
                .load(album.cover)
                .into(viewDataBinding.albumCoverImageView)

            // Set click listener
            viewDataBinding.albumCoverImageView.setOnClickListener {
                val context: Context = viewDataBinding.albumCoverImageView.context
                val intent = Intent(context, AlbumDetailActivity::class.java)
                intent.putExtra("albumId", album.albumId)
                context.startActivity(intent)
            }
        }
    }
}
