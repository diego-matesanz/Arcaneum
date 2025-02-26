package com.diego.matesanz.arcaneum.framework.remote.datasources

import com.diego.matesanz.arcaneum.framework.toHttps
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.framework.remote.models.RemoteBooks.RemoteBook
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.framework.remote.BooksService
import org.koin.core.annotation.Factory

@Factory
internal class BooksServerDataSource(
    private val booksService: BooksService,
): BooksRemoteDataSource {

    override suspend fun findBooksBySearchText(search: String): List<Book> =
        booksService
            .findBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    override suspend fun findBookById(id: String): Book =
        booksService
            .findBookById(id)
            .toDomainModel()

    override suspend fun findBookByIsbn(isbn: String): Book =
        booksService
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
        language = volumeInfo?.language?.uppercase() ?: "",
        averageRating = volumeInfo?.averageRating ?: 0.0,
        ratingsCount = volumeInfo?.ratingsCount ?: 0
    )
