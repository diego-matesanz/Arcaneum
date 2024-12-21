package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform

class BooksRepository(
    private val remoteDataSource: BooksRemoteDataSource,
    private val localDataSource: BooksLocalDataSource,
    private val shelvesRepository: ShelvesRepository,
) {

    suspend fun findBooksBySearchText(search: String): Flow<List<Book>> =
        savedBooks.transform { savedBooks ->
            val remoteBooks = remoteDataSource.findBooksBySearchText(search)
            emit(remoteBooks.map { book ->
                savedBooks.find { it.bookId == book.bookId } ?: book
            })
        }

    fun findBookById(id: String): Flow<Book?> =
        localDataSource.findSavedBookById(id).transform { localBook ->
            val book = localBook ?: remoteDataSource.findBookById(id)
            emit(book)
        }

    suspend fun findBookByIsbn(isbn: String): Book =
        remoteDataSource.findBookByIsbn(isbn)

    val savedBooks: Flow<List<Book>> = localDataSource.getSavedBooks()

    fun findSavedBooksByShelfId(shelfId: Int): Flow<List<Book>?> =
        savedBooks.transform { savedBooks ->
            emit(savedBooks.filter { book -> book.shelfId == shelfId })
        }

    val booksByShelf: Flow<Map<Shelf, List<Book>>> =
        combine(shelvesRepository.shelves, savedBooks) { shelves, savedBooks ->
            shelves.associate { shelf ->
                shelf to savedBooks.filter { book -> book.shelfId == shelf.shelfId }
            }
        }

    suspend fun saveBook(book: Book) = localDataSource.saveBook(book)

    suspend fun deleteBook(bookId: String) = localDataSource.deleteBook(bookId)
}
