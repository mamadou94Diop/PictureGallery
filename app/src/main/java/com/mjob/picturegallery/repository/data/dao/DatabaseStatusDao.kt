package com.mjob.picturegallery.repository.data.dao

import androidx.room.*
import com.mjob.picturegallery.repository.data.model.DatabaseStatus

@Dao
interface DatabaseStatusDao {
    @Query("select * from database_status limit 1")
    fun findDatabaseStatus() : DatabaseStatus?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setDatabaseStatus( databaseStatus: DatabaseStatus)
}