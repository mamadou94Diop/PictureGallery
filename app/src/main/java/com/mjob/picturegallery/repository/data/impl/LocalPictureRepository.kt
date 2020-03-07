package com.mjob.picturegallery.repository.data.impl

import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.PictureDataRepository
import com.mjob.picturegallery.repository.data.dao.DatabaseStatusDao
import com.mjob.picturegallery.repository.data.dao.PictureDao
import com.mjob.picturegallery.repository.data.model.DatabaseStatus
import com.mjob.picturegallery.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalPictureDataRepository @Inject constructor(
    private val pictureDao: PictureDao,
    private val databaseStatusDao: DatabaseStatusDao,
    private val coroutineContextProvider: CoroutineContextProvider
) : PictureDataRepository {
    override suspend fun insert(pictures: List<Picture>) {
        withContext(coroutineContextProvider.io) {
            pictureDao.insertAll(pictures)
        }
    }

    override suspend fun getAlbums() = pictureDao.findAlbums()

    override suspend fun getPicturesByAlbum(albumId: Int) = pictureDao.findPicturesByAlbum(albumId)

    override suspend fun setDatabaseStatus(databaseStatus: DatabaseStatus) {
        withContext(coroutineContextProvider.io) {
            databaseStatusDao.setDatabaseStatus(databaseStatus)
        }
    }

    override suspend fun getDatabaseStatus(): DatabaseStatus? = databaseStatusDao.findDatabaseStatus()
}
