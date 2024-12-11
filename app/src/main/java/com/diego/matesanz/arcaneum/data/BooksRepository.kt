package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class BooksRepository(
    private val remoteDataSource: BooksRemoteDataSource,
    private val localDataSource: BooksLocalDataSource,
) {

    suspend fun findBooksBySearchText(search: String): List<Book> =
        remoteDataSource.findBooksBySearchText(search)

    fun findBookById(id: String): Flow<Book?> =
        localDataSource.findSavedBookById(id).transform { localBook ->
            val book = localBook ?: remoteDataSource.findBookById(id)
            emit(book)
        }

    suspend fun findBookByIsbn(isbn: String): Book =
        remoteDataSource.findBookByIsbn(isbn)

    val savedBooks: Flow<List<Book>> = localDataSource.getSavedBooks()

    fun findSavedBooksByShelfId(shelfId: Int): Flow<List<Book>?> =
        localDataSource.findSavedBooksByShelfId(shelfId)

    suspend fun saveBook(book: Book) = localDataSource.saveBook(book)

    suspend fun deleteBook(bookId: String) = localDataSource.deleteBook(bookId)
}
