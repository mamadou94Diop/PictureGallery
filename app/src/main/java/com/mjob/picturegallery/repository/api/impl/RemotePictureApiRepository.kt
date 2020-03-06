package com.mjob.picturegallery.repository.api.impl

import com.mjob.picturegallery.repository.api.PictureApiService
import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.utils.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePictureApiRepository @Inject constructor(
    private val pictureApiService: PictureApiService,
    private val coroutineContextProvider: CoroutineContextProvider
) :
    PictureApiRepository {
    override suspend fun get(): List<Picture>? {
       var res : List<Picture>? = null
        withContext(coroutineContextProvider.io) {
            val execute = pictureApiService.getPictures().execute()
            res =  execute.body()
        }

        return res
    }
}
