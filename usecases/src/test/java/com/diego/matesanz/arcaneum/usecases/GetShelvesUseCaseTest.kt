package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetShelvesUseCaseTest {

    @Test
    fun `Use case invoke calls repository`() {
        // Given
        val shelvesFlow = flowOf(sampleShelves(1, 2, 3, 4))
        val useCase = GetShelvesUseCase(mock<ShelvesRepository> {
            on { shelves } doReturn shelvesFlow
        })

        // When
        val result = useCase()

        // Then
        assertEquals(shelvesFlow, result)
    }
}
