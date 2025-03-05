package com.diego.matesanz.arcaneum.data.repositories

import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ShelvesRepositoryTest {

    @Mock
    lateinit var localDataSource: ShelvesLocalDataSource

    private lateinit var repository: ShelvesRepository

    @Before
    fun setUp() {
        repository = ShelvesRepository(localDataSource)
    }

    @Test
    fun `Shelves are fetched from local data source`(): Unit =
        runBlocking {
            // Given
            val shelves = sampleShelves(1, 2, 3, 4)
            whenever(localDataSource.getShelves()).thenReturn(flowOf(shelves))

            // When
            val result = repository.shelves

            // Then
            assertEquals(shelves, result.first())
        }

    @Test
    fun `Save shelf invokes save shelf from local data source`(): Unit =
        runBlocking {
            // Given
            val shelf = sampleShelf(1)

            // When
            repository.saveShelf(shelf)

            // Then
            verify(localDataSource).saveShelf(shelf)
        }

    @Test
    fun `Delete shelf invokes delete shelf from local data source if shelf is removable`(): Unit =
        runBlocking {
            // Given
            val shelf = sampleShelf(1).copy(isRemovable = true)

            // When
            repository.deleteShelf(shelf)

            // Then
            verify(localDataSource).deleteShelf(shelf.shelfId)
        }

    @Test
    fun `Delete shelf does not invoke delete shelf from local data source if shelf is not removable`(): Unit =
        runBlocking {
            // Given
            val shelf = sampleShelf(1).copy(isRemovable = false)

            // When
            repository.deleteShelf(shelf)

            // Then
            verify(localDataSource, never()).deleteShelf(shelf.shelfId)
        }
}
