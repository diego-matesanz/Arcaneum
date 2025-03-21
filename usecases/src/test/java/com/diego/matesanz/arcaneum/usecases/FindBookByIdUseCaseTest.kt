package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.test.unit.domain.book.sampleBook
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindBookByIdUseCaseTest {

    @Test
    fun `Use case invoke calls repository`() {
        // Given
        val bookFlow = flowOf(sampleBook("1"))
        val useCase = FindBookByIdUseCase(mock<BooksRepository> {
            on { findBookById("1") } doReturn bookFlow
        })

        // When
        val result = useCase("1")

        // Then
        assertEquals(bookFlow, result)
    }
}
