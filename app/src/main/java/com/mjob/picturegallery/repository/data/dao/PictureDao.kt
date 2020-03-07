package com.mjob.picturegallery.repository.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mjob.picturegallery.repository.api.model.Picture

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pictures: List<Picture>)

    @Query("SELECT distinct(albumId) FROM picture")
    fun findAlbums(): DataSource.Factory<Int, Int>

    @Query("SELECT * FROM picture WHERE albumId=:albumId")
    fun findPicturesByAlbum(albumId : Int): DataSource.Factory<Int, Picture>

    @Query("SELECT count(*) FROM picture")
    fun size(): Int
}