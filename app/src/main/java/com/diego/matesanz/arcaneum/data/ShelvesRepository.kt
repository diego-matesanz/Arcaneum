package com.diego.matesanz.arcaneum.data

import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseConstants.CURRENTLY_READING_SHELF_ID
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseConstants.READ_SHELF_ID
import com.diego.matesanz.arcaneum.data.datasource.database.DatabaseConstants.WANT_TO_READ_SHELF_ID
import kotlinx.coroutines.flow.Flow

class ShelvesRepository(
    private val localDataSource: ShelvesLocalDataSource,
) {

    val shelves: Flow<List<Shelf>> = localDataSource.getShelves()

    suspend fun saveShelf(shelf: Shelf) = localDataSource.saveShelf(shelf)

    suspend fun deleteShelf(shelf: Shelf) {
        if (shelf.isRemovable) {
            localDataSource.deleteShelf(shelf.shelfId)
        }
    }
}
