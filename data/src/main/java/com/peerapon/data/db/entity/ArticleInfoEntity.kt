package com.peerapon.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomWarnings

@Entity
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
data class ArticleInfoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "abstract") val abstractText: String,
    @ColumnInfo(name = "bigThumbnailUrl") val bigThumbnailUrl: String,
    @ColumnInfo(name = "period") val period: Int = 1
)