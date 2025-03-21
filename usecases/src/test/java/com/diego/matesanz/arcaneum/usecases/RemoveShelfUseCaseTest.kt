package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.test.unit.domain.shelf.sampleShelf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RemoveShelfUseCaseTest {

    @Test
    fun `Use case invoke calls repository`(): Unit = runBlocking {
        // Given
        val shelf = sampleShelf(1)
        val repository = mock<ShelvesRepository>()
        val useCase = RemoveShelfUseCase(repository)

        // When
        useCase(shelf)

        // Then
        verify(repository).deleteShelf(shelf)
    }
}
