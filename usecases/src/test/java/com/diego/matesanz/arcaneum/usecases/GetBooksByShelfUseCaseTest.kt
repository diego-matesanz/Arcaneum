package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.test.unit.sampleBooks
import com.diego.matesanz.arcaneum.test.unit.sampleShelf
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetBooksByShelfUseCaseTest {

    @Test
    fun `Use case invoke calls repository`() {
        // Given
        val booksByShelfFlow = flowOf(mapOf(sampleShelf(1) to sampleBooks("1", "2", "3", "4")))
        val useCase = GetBooksByShelfUseCase(mock<BooksRepository> {
            on { booksByShelf } doReturn booksByShelfFlow
        })

        // When
        val result = useCase()

        // Then
        assertEquals(booksByShelfFlow, result)
    }
}
