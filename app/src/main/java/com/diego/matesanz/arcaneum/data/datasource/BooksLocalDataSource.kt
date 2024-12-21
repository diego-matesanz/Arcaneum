package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.datasource.database.BooksDao
import kotlinx.coroutines.flow.Flow

class BooksLocalDataSource(
    private val booksDao: BooksDao,
) {
    fun getSavedBooks(): Flow<List<Book>> = booksDao.getSavedBooks()

    fun findSavedBookById(bookId: String): Flow<Book?> = booksDao.findSavedBookById(bookId)

    suspend fun saveBook(book: Book) = booksDao.saveBook(book)

    suspend fun deleteBook(bookId: String) = booksDao.deleteBook(bookId)
}
