package com.diego.matesanz.arcaneum.data.datasource

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.data.datasource.remote.BooksClient
import com.diego.matesanz.arcaneum.data.datasource.remote.RemoteBooks.RemoteBook
import com.diego.matesanz.arcaneum.data.common.utils.toHttps

class BooksRemoteDataSource {

    suspend fun findBooksBySearchText(search: String): List<Book> =
        BooksClient
            .instance
            .findBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    suspend fun findBookById(id: String): Book =
        BooksClient
            .instance
            .findBookById(id)
            .toDomainModel()

    suspend fun findBookByIsbn(isbn: String): Book =
        BooksClient
            .instance
            .findBooksBySearchText("isbn:$isbn")
            .items
            .first()
            .toDomainModel()
}

private fun RemoteBook.toDomainModel(): Book =
    Book(
        bookId = id ?: "",
        shelfId = Int.MAX_VALUE,
        title = volumeInfo?.title ?: "",
        authors = volumeInfo?.authors ?: emptyList(),
        coverImage = volumeInfo?.imageLinks?.thumbnail?.toHttps() ?: "",
        pageCount = volumeInfo?.pageCount ?: 0,
        description = volumeInfo?.description ?: "",
        language = volumeInfo?.language?.toUpperCase(Locale.current) ?: "",
        averageRating = volumeInfo?.averageRating ?: 0.0,
        ratingsCount = volumeInfo?.ratingsCount ?: 0
    )
