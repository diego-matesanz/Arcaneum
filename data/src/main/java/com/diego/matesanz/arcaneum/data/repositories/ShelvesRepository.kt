package com.diego.matesanz.arcaneum.data.repositories

import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ShelvesRepository @Inject constructor(
    private val localDataSource: ShelvesLocalDataSource,
) {

    val shelves: Flow<List<Shelf>>
        get() = localDataSource.getShelves()

    suspend fun saveShelf(shelf: Shelf) =
        localDataSource.saveShelf(shelf)

    suspend fun deleteShelf(shelf: Shelf) {
        if (shelf.isRemovable) {
            localDataSource.deleteShelf(shelf.shelfId)
        }
    }
}
