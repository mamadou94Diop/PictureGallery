package com.mjob.picturegallery.repository.api

import com.mjob.picturegallery.repository.api.model.Picture
import retrofit2.Call
import retrofit2.http.GET

interface PictureApiService {
    @GET("/img/shared/technical-test.json")
    fun getPictures(
    ): Call<List<Picture>>
}