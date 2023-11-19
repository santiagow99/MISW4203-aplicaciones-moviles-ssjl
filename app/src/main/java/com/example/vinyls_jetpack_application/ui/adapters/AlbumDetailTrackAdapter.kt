package com.example.vinyls_jetpack_application.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls_jetpack_application.databinding.AlbumDetailTrackItemBinding
import com.example.vinyls_jetpack_application.models.Track

class AlbumDetailTrackAdapter: RecyclerView.Adapter<AlbumDetailTrackAdapter.TrackViewHolder>() {
    var tracks :List<Track> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    class TrackViewHolder(val viewDataBinding: AlbumDetailTrackItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = com.example.vinyls_jetpack_application.R.layout.album_detail_track_item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val withDataBinding: AlbumDetailTrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            TrackViewHolder.LAYOUT,
            parent,
            false
        )
        return TrackViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }
    }

}