package com.mjob.picturegallery.repository.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.dao.DatabaseStatusDao
import com.mjob.picturegallery.repository.data.dao.PictureDao
import com.mjob.picturegallery.repository.data.model.DatabaseStatus

@Database(entities = [Picture::class, DatabaseStatus::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun pictureDao(): PictureDao
    abstract fun databaseStatusDao(): DatabaseStatusDao
}