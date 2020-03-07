package com.mjob.picturegallery.repository.api

import com.mjob.picturegallery.repository.api.model.Picture
interface PictureApiRepository {
   @Throws(Exception::class)
   suspend fun get(): List<Picture>?
}