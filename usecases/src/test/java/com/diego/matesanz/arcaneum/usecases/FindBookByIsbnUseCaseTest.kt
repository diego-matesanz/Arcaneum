package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.test.unit.sampleBook
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindBookByIsbnUseCaseTest {

    @Test
    fun `Use case invoke calls repository`(): Unit = runBlocking {
        // Given
        val book = sampleBook("1")
        val useCase = FindBookByIsbnUseCase(mock<BooksRepository> {
            onBlocking { findBookByIsbn("123456H") } doReturn book
        })

        // When
        val result = useCase("123456H")

        // Then
        assertEquals(book, result)
    }
}
