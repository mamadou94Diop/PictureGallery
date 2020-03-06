package com.mjob.picturegallery.interactor

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.PictureDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PictureInteractor @Inject constructor(
    private val pictureDataRepository: PictureDataRepository,
    private val pictureApiRepository: PictureApiRepository
) {
    lateinit var pictures : DataSource.Factory<Int, Picture>
    suspend fun getPictures(): DataSource.Factory<Int, Picture> {
        withContext(Dispatchers.IO) {
         val x = pictureDataRepository.size() == 5000
            if (!x) {
                val remotePictures = pictureApiRepository.get()
                pictureDataRepository.insert(remotePictures!!)
            }
            pictures = pictureDataRepository.get()

        }

        return pictures
    }
}