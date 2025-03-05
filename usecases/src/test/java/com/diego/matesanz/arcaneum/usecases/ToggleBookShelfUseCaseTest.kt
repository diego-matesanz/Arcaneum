package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.test.unit.sampleBook
import com.diego.matesanz.arcaneum.test.unit.sampleShelf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleBookShelfUseCaseTest {

    @Test
    fun `Use case invoke calls repository delete book when book's shelfId and shelf's shelfId are the same`(): Unit =
        runBlocking {
            // Given
            val shelf = sampleShelf(1)
            val book = sampleBook("1")
            val repository = mock<BooksRepository>()
            val useCase = ToggleBookShelfUseCase(repository)

            // When
            useCase(shelf.shelfId, book)

            // Then
            verify(repository).deleteBook(book.bookId)
        }

    @Test
    fun `Use case invoke calls repository save book when book's shelfId and shelf's shelfId are different`(): Unit =
        runBlocking {
            // Given
            val shelf = sampleShelf(2)
            val book = sampleBook("1")
            val repository = mock<BooksRepository>()
            val useCase = ToggleBookShelfUseCase(repository)

            // When
            useCase(shelf.shelfId, book)

            // Then
            verify(repository).saveBook(book.copy(shelfId = shelf.shelfId))
        }
}
