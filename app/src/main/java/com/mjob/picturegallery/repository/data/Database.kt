package com.mjob.picturegallery.repository.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.dao.PictureDao

@Database(entities = [Picture::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun pictureDao(): PictureDao
}