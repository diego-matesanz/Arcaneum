package com.diego.matesanz.arcaneum.domain

data class Book(
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
