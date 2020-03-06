package com.mjob.picturegallery.repository.data.impl

import androidx.paging.DataSource
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.PictureDataRepository
import com.mjob.picturegallery.repository.data.dao.PictureDao
import com.mjob.picturegallery.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalPictureDataRepository @Inject constructor(
    private val dao: PictureDao,
    private val coroutineContextProvider: CoroutineContextProvider
) : PictureDataRepository {
    override suspend fun insert(pictures: List<Picture>) {
        withContext(coroutineContextProvider.io) {
            dao.insertAll(pictures)
        }
    }

    override suspend fun size(): Int {
        return dao.size()
    }

    override suspend  fun get() : DataSource.Factory<Int, Picture> = dao.getPictures()

}
