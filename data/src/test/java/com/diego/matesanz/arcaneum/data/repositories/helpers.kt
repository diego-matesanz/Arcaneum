package com.diego.matesanz.arcaneum.data.repositories

import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf

fun sampleBook(bookId: String) = Book(
    bookId = bookId,
    shelfId = 1,
    title = "Sample Title",
    authors = arrayListOf("Sample Author 1", "Sample Author 2"),
    coverImage = "Cover image",
    pageCount = 300,
    description = "Description",
    language = "EN",
    averageRating = 4.5,
    ratingsCount = 450,
)

fun sampleBooks(vararg bookIds: String) = bookIds.map { sampleBook(it) }

fun sampleShelf(shelfId: Int) = Shelf(
    shelfId = shelfId,
    name = "Sample Shelf",
)

fun sampleShelves(vararg shelfIds: Int) = shelfIds.map { sampleShelf(it) }
