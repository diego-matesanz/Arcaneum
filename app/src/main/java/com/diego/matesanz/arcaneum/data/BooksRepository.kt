package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource

class BooksRepository(
    private val remoteDataSource: BooksRemoteDataSource,
) {

    suspend fun fetchBooksBySearchText(search: String): List<Book> =
        remoteDataSource.fetchBooksBySearchText(search)

    suspend fun fetchBookById(id: String): Book =
        remoteDataSource.fetchBookById(id)

    suspend fun fetchBookByIsbn(isbn: String): Book =
        remoteDataSource.fetchBookByIsbn(isbn)
}
