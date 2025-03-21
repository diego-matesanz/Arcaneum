package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.domain.Book

interface BooksRemoteDataSource {
    suspend fun findBooksBySearchText(search: String): List<Book>
    suspend fun findBookById(id: String): Book
    suspend fun findBookByIsbn(isbn: String): Book
}
