package com.mjob.picturegallery.repository.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "picture")
data class Picture(
    @PrimaryKey
    @ColumnInfo(name ="id")
    @SerializedName("id")
    val id: Int,

    @SerializedName("albumId")
    @ColumnInfo(name ="albumId")
    var albumId: Int,

    @ColumnInfo(name ="title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name ="url")
    @SerializedName("url")
    val url: String,

    @ColumnInfo(name ="thumbnailUrl")
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
): Serializable