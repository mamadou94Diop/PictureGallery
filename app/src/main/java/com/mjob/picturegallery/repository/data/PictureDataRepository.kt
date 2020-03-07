package com.mjob.picturegallery.repository.data

import androidx.paging.DataSource
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.model.DatabaseStatus

interface PictureDataRepository {
    suspend fun insert(pictures: List<Picture>)
    suspend fun getAlbums(): DataSource.Factory<Int, Int>
    suspend fun getPicturesByAlbum(albumId: Int): DataSource.Factory<Int, Picture>
    suspend fun setDatabaseStatus(databaseStatus: DatabaseStatus)
    suspend fun getDatabaseStatus() : DatabaseStatus?
}