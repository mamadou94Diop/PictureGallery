package com.mjob.picturegallery.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mjob.picturegallery.R
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.ui.fragments.OnPictureItemClickListener
import com.mjob.picturegallery.utils.loadImageFromUrl
import javax.inject.Inject

class PictureListAdapter @Inject constructor() :
    PagedListAdapter<Picture, PictureViewHolder>(
        PICTURE_COMPARATOR
    ) {
    var listener: OnPictureItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_picture, parent, false)
        return PictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val picture = getItem(position)
        holder.bindTo(picture)
        holder.itemView.setOnClickListener {
            listener?.openPicture(picture)
        }
    }

    companion object {
        private val PICTURE_COMPARATOR = object : DiffUtil.ItemCallback<Picture>() {
            override fun areItemsTheSame(oldPicture: Picture, newPicture: Picture): Boolean =
                oldPicture.id == newPicture.id

            override fun areContentsTheSame(oldPicture: Picture, newPicture: Picture): Boolean =
                oldPicture == newPicture
        }
    }
}


class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(picture: Picture?) {
        picture?.let {
            val pictureThumbnail: ImageView = itemView.findViewById(R.id.picture_thumbnail)
            pictureThumbnail.loadImageFromUrl(picture.thumbnailUrl)

            val pictureId: TextView = itemView.findViewById(R.id.picture_id_value)
            pictureId.text = "${picture.id}"

            val pictureTitle: TextView = itemView.findViewById(R.id.picture_title_value)
            pictureTitle.text = picture.title


        }
    }
}
