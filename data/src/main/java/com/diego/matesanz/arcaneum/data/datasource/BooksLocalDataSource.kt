package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.domain.Book
import kotlinx.coroutines.flow.Flow

interface BooksLocalDataSource {
    fun getSavedBooks(): Flow<List<Book>>
    fun findSavedBookById(bookId: String): Flow<Book?>
    suspend fun saveBook(book: Book)
    suspend fun deleteBook(bookId: String)
}
