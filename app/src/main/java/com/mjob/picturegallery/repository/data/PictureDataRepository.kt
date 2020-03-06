package com.mjob.picturegallery.repository.data

import androidx.paging.DataSource
import com.mjob.picturegallery.repository.api.model.Picture

interface PictureDataRepository {
    suspend fun insert(pictures: List<Picture>)
    suspend fun get(): DataSource.Factory<Int, Picture>
    suspend fun size(): Int

}