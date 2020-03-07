package com.mjob.picturegallery.repository.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "database_status")
data class DatabaseStatus(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 1,

    @ColumnInfo
    val statusCode: Int
)


class DatabaseStatusCode() {
    companion object {
        const val SUCCESS: Int = 1
        const val Failure: Int = -1
    }

}