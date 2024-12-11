package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.datasource.database.ShelvesDao
import kotlinx.coroutines.flow.Flow

class ShelvesLocalDataSource(
    private val shelvesDao: ShelvesDao,
) {

    fun getShelves(): Flow<List<Shelf>> = shelvesDao.getShelves()

    fun findShelfById(shelfId: Int): Flow<Shelf?> = shelvesDao.findShelfById(shelfId)

    suspend fun saveShelf(shelf: Shelf) = shelvesDao.saveShelf(shelf)

    suspend fun deleteShelf(shelfId: Int) = shelvesDao.deleteShelf(shelfId)
}
