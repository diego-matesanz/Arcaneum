package com.diego.matesanz.arcaneum.test.unit.data

import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

fun buildBooksRepositoryWith(
    localBooks: List<Book> = emptyList(),
    remoteBooks: List<Book> = emptyList(),
    localShelves: List<Shelf> = emptyList(),
): BooksRepository {
    val fakeBooksRemoteDataSource = FakeBooksRemoteDataSource(remoteBooks)
    val fakeBooksLocalDataSource = FakeBooksLocalDataSource(localBooks)
    val shelvesRepository = buildShelvesRepositoryWith(localShelves)

    return BooksRepository(
        shelvesRepository = shelvesRepository,
        remoteDataSource = fakeBooksRemoteDataSource,
        localDataSource = fakeBooksLocalDataSource,
    )
}

fun buildShelvesRepositoryWith(
    localShelves: List<Shelf> = emptyList(),
): ShelvesRepository {
    val fakeShelvesLocalDataSource = FakeShelvesLocalDataSource(localShelves)

    return ShelvesRepository(
        localDataSource = fakeShelvesLocalDataSource,
    )
}

class FakeBooksRemoteDataSource(
    books: List<Book> = emptyList(),
) : BooksRemoteDataSource {

    val books = MutableStateFlow<List<Book>>(books)

    override suspend fun findBooksBySearchText(search: String): List<Book> = books.value

    override suspend fun findBookById(id: String): Book =
        books.value.first { book -> book.bookId == id }

    override suspend fun findBookByIsbn(isbn: String): Book =
        books.value.first { book -> book.bookId == isbn }
}

class FakeBooksLocalDataSource(
    books: List<Book> = emptyList(),
) : BooksLocalDataSource {

    val inMemoryBooks = MutableStateFlow<List<Book>>(books)

    override fun getSavedBooks(): Flow<List<Book>> = inMemoryBooks

    override fun findSavedBookById(bookId: String): Flow<Book?> =
        inMemoryBooks.map { books -> books.firstOrNull { book -> book.bookId == bookId } }

    override suspend fun saveBook(book: Book) {
        inMemoryBooks.value.find { it.bookId == book.bookId }?.let { bookMatch ->
            inMemoryBooks.value = inMemoryBooks.value - bookMatch + book
        } ?: run {
            inMemoryBooks.value = inMemoryBooks.value + book
        }
    }

    override suspend fun deleteBook(bookId: String) {
        inMemoryBooks.value.find { it.bookId == bookId }?.let { bookMatch ->
            inMemoryBooks.value = inMemoryBooks.value - bookMatch
        }
    }
}

class FakeShelvesLocalDataSource(
    shelves: List<Shelf> = emptyList(),
) : ShelvesLocalDataSource {

    val inMemoryShelves = MutableStateFlow<List<Shelf>>(shelves)

    override fun getShelves(): Flow<List<Shelf>> = inMemoryShelves

    override suspend fun saveShelf(shelf: Shelf) {
        inMemoryShelves.value = inMemoryShelves.value + shelf
    }

    override suspend fun deleteShelf(shelfId: Int) {
        inMemoryShelves.value.find { it.shelfId == shelfId }?.let { shelfMatch ->
            inMemoryShelves.value = inMemoryShelves.value + shelfMatch
        }
    }
}
