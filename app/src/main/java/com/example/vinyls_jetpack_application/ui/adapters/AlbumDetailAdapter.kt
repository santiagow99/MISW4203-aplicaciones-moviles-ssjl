package com.example.vinyls_jetpack_application.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.vinyls_jetpack_application.databinding.AlbumDetailItemBinding
import com.example.vinyls_jetpack_application.models.AlbumDetail

class AlbumDetailAdapter : BaseAdapter() {

    var album: AlbumDetail? = null

    override fun getCount(): Int {
        return 1 // Solo hay un elemento de detalle de álbum
    }

    override fun getItem(position: Int): Any? {
        return album
    }

    override fun getItemId(position: Int): Long {
        return 0 // No se utiliza en este caso
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = AlbumDetailItemBinding.inflate(inflater, parent, false)
        album?.let { binding.albumDetail = it }
        return binding.root
    }
}

