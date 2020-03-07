package com.mjob.picturegallery.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjob.picturegallery.R
import com.mjob.picturegallery.ui.fragments.OnAlbumEventListener
import javax.inject.Inject

class AlbumListAdapter @Inject constructor() :
    PagedListAdapter<Int, AlbumViewHolder>(ALBUM_COMPARATOR) {
    var listener: OnAlbumEventListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_album_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holderAlbum: AlbumViewHolder, position: Int) {
        val albumId = getItem(position)
        holderAlbum.bindTo(albumId,listener)
    }

    companion object {
        private val ALBUM_COMPARATOR = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        }
    }
}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(
        albumId: Int?,
        listener: OnAlbumEventListener?
    ) {
        val albumButton: Button = itemView.findViewById(R.id.album_id)
        albumButton.text = albumId.toString()
        albumButton.setOnClickListener {
           listener?.openAlbum(albumId!!)
        }
    }
}
