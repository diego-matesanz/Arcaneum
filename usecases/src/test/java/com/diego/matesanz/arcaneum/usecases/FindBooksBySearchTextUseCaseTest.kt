package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.test.unit.domain.book.sampleBooks
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindBooksBySearchTextUseCaseTest {

    @Test
    fun `Use case invoke calls repository`() {
        // Given
        val booksFlow = flowOf(sampleBooks("1", "2", "3", "4"))
        val useCase = FindBooksBySearchTextUseCase(mock<BooksRepository> {
            on { findBooksBySearchText("test") } doReturn booksFlow
        })

        // When
        val result = useCase("test")

        // Then
        assertEquals(booksFlow, result)
    }
}
