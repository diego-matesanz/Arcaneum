package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow

interface ShelvesLocalDataSource {
    fun getShelves(): Flow<List<Shelf>>
    suspend fun saveShelf(shelf: Shelf)
    suspend fun deleteShelf(shelfId: Int)
}
