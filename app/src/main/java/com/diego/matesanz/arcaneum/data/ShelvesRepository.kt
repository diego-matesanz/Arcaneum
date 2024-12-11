package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import kotlinx.coroutines.flow.Flow

class ShelvesRepository(
    private val localDataSource: ShelvesLocalDataSource,
) {

    val shelves: Flow<List<Shelf>> = localDataSource.getShelves()

    fun findShelfById(shelfId: Int): Flow<Shelf?> = localDataSource.findShelfById(shelfId)

    suspend fun saveShelf(shelf: Shelf) = localDataSource.saveShelf(shelf)

    suspend fun deleteShelf(shelfId: Int) = localDataSource.deleteShelf(shelfId)
}
