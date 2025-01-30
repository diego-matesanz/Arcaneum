package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.data.datasource.database.dao.BooksDao
import com.diego.matesanz.arcaneum.data.datasource.database.entities.BookEntity
import com.diego.matesanz.arcaneum.domain.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BooksLocalDataSource(
    private val booksDao: BooksDao,
) {
    fun getSavedBooks(): Flow<List<Book>> = booksDao.getSavedBooks().map { books -> books.map { it.toDomainModel() } }

    fun findSavedBookById(bookId: String): Flow<Book?> = booksDao.findSavedBookById(bookId).map { it?.toDomainModel() }

    suspend fun saveBook(book: Book) = booksDao.saveBook(book.toEntity())

    suspend fun deleteBook(bookId: String) = booksDao.deleteBook(bookId)
}

private fun BookEntity.toDomainModel(): Book = Book(
    bookId = bookId,
    shelfId = shelfId,
    title = title,
    authors = authors,
    coverImage = coverImage,
    pageCount = pageCount,
    description = description,
    language = language,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
)

private fun Book.toEntity(): BookEntity = BookEntity(
    bookId = bookId,
    shelfId = shelfId,
    title = title,
    authors = authors,
    coverImage = coverImage,
    pageCount = pageCount,
    description = description,
    language = language,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
)
