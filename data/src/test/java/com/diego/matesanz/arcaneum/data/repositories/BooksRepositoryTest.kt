package com.diego.matesanz.arcaneum.data.repositories

import com.diego.matesanz.arcaneum.data.datasource.BooksLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.BooksRemoteDataSource
import com.diego.matesanz.arcaneum.test.unit.domain.book.sampleBook
import com.diego.matesanz.arcaneum.test.unit.domain.book.sampleBooks
import com.diego.matesanz.arcaneum.test.unit.domain.shelf.sampleShelves
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class BooksRepositoryTest {

    @Mock
    lateinit var shelvesRepository: ShelvesRepository

    @Mock
    lateinit var remoteDataSource: BooksRemoteDataSource

    @Mock
    lateinit var localDataSource: BooksLocalDataSource

    private lateinit var repository: BooksRepository

    @Before
    fun setUp() {
        repository = BooksRepository(
            shelvesRepository = shelvesRepository,
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
        )
    }

    @Test
    fun `Books by shelf are fetched from local data source and associated with local shelves`(): Unit =
        runBlocking {
            // Given
            val localBooks = sampleBooks("1", "2", "3", "4").map { it.copy(shelfId = it.bookId.toInt()) }
            val localShelves = sampleShelves(1, 2, 3, 4)
            val expectedMap = localShelves.associate { shelf ->
                shelf to localBooks.filter { book -> book.shelfId == shelf.shelfId }
            }
            whenever(localDataSource.getSavedBooks()).thenReturn(flowOf(localBooks))
            whenever(shelvesRepository.shelves).thenReturn(flowOf(localShelves))

            // When
            val result = repository.booksByShelf

            // Then
            assertEquals(expectedMap, result.first())
        }

    @Test
    fun `Books by search text is fetched from remote data source and merged with local data`(): Unit =
        runBlocking {
            // Given
            val localBooks = sampleBooks("1", "2", "3", "4").map { it.copy(shelfId = 2) }
            val remoteBooks = sampleBooks("4", "6", "7", "8")
            val mergedBooks = remoteBooks.map { book ->
                localBooks.find { it.bookId == book.bookId } ?: book
            }
            val searchText = "search"
            whenever(localDataSource.getSavedBooks()).thenReturn(flowOf(localBooks))
            whenever(remoteDataSource.findBooksBySearchText(searchText)).thenReturn(remoteBooks)

            // When
            val result = repository.findBooksBySearchText(searchText)

            // Then
            assertEquals(mergedBooks, result.first())
        }

    @Test
    fun `Book by id is fetched from remote data source when local data is empty`(): Unit =
        runBlocking {
            // Given
            val localBook = null
            val remoteBook = sampleBook("1")
            whenever(localDataSource.findSavedBookById("1")).thenReturn(flowOf(localBook))
            whenever(remoteDataSource.findBookById("1")).thenReturn(remoteBook)

            // When
            val result = repository.findBookById("1")

            // Then
            assertEquals(remoteBook, result.first())
        }

    @Test
    fun `Book by id is fetched from local data source when local data is not empty`(): Unit =
        runBlocking {
            // Given
            val localBook = sampleBook("1")
            whenever(localDataSource.findSavedBookById("1")).thenReturn(flowOf(localBook))

            // When
            val result = repository.findBookById("1")

            // Then
            assertEquals(localBook, result.first())
        }

    @Test
    fun `Books by shelfId are fetched from local data source and filtered by shelfId`(): Unit =
        runBlocking {
            // Given
            val localBooks = sampleBooks("1", "2", "3", "4").map { it.copy(shelfId = it.bookId.toInt()) }
            whenever(localDataSource.getSavedBooks()).thenReturn(flowOf(localBooks))

            // When
            val result = repository.findSavedBooksByShelfId(1)

            // Then
            assertEquals(localBooks.filter { it.shelfId == 1 }, result.first())
        }

    @Test
    fun `Book by isbn is fetched from remote data source`(): Unit =
        runBlocking {
            // Given
            val remoteBook = sampleBook("1")
            whenever(remoteDataSource.findBookByIsbn("123456H")).thenReturn(remoteBook)

            // When
            val result = repository.findBookByIsbn("123456H")

            // Then
            assertEquals(remoteBook, result)
        }

    @Test
    fun `Save book invokes save book from local data source`(): Unit =
        runBlocking {
            // Given
            val book = sampleBook("1")

            // When
            repository.saveBook(book)

            // Then
            verify(localDataSource).saveBook(book)
        }

    @Test
    fun `Delete book invokes delete book from local data source`(): Unit =
        runBlocking {
            // Given
            val book = sampleBook("1")

            // When
            repository.deleteBook(book.bookId)

            // Then
            verify(localDataSource).deleteBook(book.bookId)
        }
}
