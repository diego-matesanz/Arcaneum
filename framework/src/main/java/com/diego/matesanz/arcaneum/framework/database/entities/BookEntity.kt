package com.diego.matesanz.arcaneum.framework.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["shelfId"])],
    foreignKeys = [
        ForeignKey(
            entity = ShelfEntity::class,
            parentColumns = arrayOf("shelfId"),
            childColumns = arrayOf("shelfId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class BookEntity(
    @PrimaryKey
    val bookId: String,
    val shelfId: Int,
    val title: String,
    val authors: List<String>,
    val coverImage: String,
    val pageCount: Int,
    val description: String,
    val language: String,
    val averageRating: Double,
    val ratingsCount: Int,
)
