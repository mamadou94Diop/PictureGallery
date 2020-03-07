package com.mjob.picturegallery.interactor

import androidx.paging.DataSource
import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.data.PictureDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureInteractor @Inject constructor(
    private val pictureDataRepository: PictureDataRepository,
    private val pictureApiRepository: PictureApiRepository
) {
    lateinit var albums : DataSource.Factory<Int, Int>

    suspend fun getAlbums(): DataSource.Factory<Int, Int> {
        withContext(Dispatchers.IO) {
         val x = pictureDataRepository.size() == 5000
            if (!x) {
                val remotePictures = pictureApiRepository.get()
                pictureDataRepository.insert(remotePictures!!)
            }
            albums = pictureDataRepository.getAlbums()

        }

        return albums
    }

    suspend fun getPicturesByAlbum(albumId: Int) = pictureDataRepository.getPicturesByAlbum(albumId)
}