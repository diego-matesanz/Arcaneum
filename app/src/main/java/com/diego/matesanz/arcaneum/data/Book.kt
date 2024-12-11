package com.diego.matesanz.arcaneum.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Shelf::class,
            parentColumns = arrayOf("shelfId"),
            childColumns = arrayOf("shelfId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ]
)
data class Book(
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
