package com.mjob.picturegallery.interactor

import androidx.paging.DataSource
import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.data.PictureDataRepository
import com.mjob.picturegallery.repository.data.model.DatabaseStatus
import com.mjob.picturegallery.repository.data.model.DatabaseStatusCode.Companion.Failure
import com.mjob.picturegallery.repository.data.model.DatabaseStatusCode.Companion.SUCCESS
import com.mjob.picturegallery.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureInteractor @Inject constructor(
    private val pictureDataRepository: PictureDataRepository,
    private val pictureApiRepository: PictureApiRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    private var albums: DataSource.Factory<Int, Int>? = null

    suspend fun getAlbums(): DataSource.Factory<Int, Int>? {
        withContext(coroutineContextProvider.io) {
            val databaseStatus = pictureDataRepository.getDatabaseStatus()

            if (databaseStatus == null || databaseStatus.statusCode == Failure) {
                try {
                    val pictures = pictureApiRepository.get()!!
                    if (pictures.isNotEmpty()) {
                        pictureDataRepository.insert(pictures)
                        pictureDataRepository.setDatabaseStatus(DatabaseStatus(statusCode = SUCCESS))
                    } else {
                        pictureDataRepository.setDatabaseStatus(DatabaseStatus(statusCode = Failure))
                    }
                } catch (exception: Exception) {
                    pictureDataRepository.setDatabaseStatus(DatabaseStatus(statusCode = Failure))
                }
            }
            albums = pictureDataRepository.getAlbums()
        }

        return albums
    }

    suspend fun getPicturesByAlbum(albumId: Int) = pictureDataRepository.getPicturesByAlbum(albumId)
}