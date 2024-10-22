package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.RemoteResult.RemoteBook

class BooksRepository {

    suspend fun fetchBooksBySearchText(search: String): List<Book> =
        BooksClient
            .instance
            .fetchBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }
}

private fun RemoteBook.toDomainModel(): Book =
    Book(
        id = id ?: "",
        title = volumeInfo?.title ?: "",
        authors = volumeInfo?.authors ?: emptyList(),
        coverImage = volumeInfo?.imageLinks?.thumbnail ?: "",
        pageCount = volumeInfo?.pageCount ?: 0,
        description = volumeInfo?.description ?: "",
        language = volumeInfo?.language ?: "",
        averageRating = volumeInfo?.averageRating ?: 0.0,
    )
