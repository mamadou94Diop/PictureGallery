package com.mjob.picturegallery.repository.api

import com.mjob.picturegallery.repository.api.model.Picture

interface PictureApiRepository {
   suspend fun get(): List<Picture>?
}