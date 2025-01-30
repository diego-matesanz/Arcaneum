package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.datasource.database.dao.ShelvesDao
import com.diego.matesanz.arcaneum.data.datasource.database.entities.ShelfEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShelvesLocalDataSource(
    private val shelvesDao: ShelvesDao,
) {

    fun getShelves(): Flow<List<Shelf>> = shelvesDao.getShelves().map { shelves -> shelves.map { it.toDomainModel() } }

    fun findShelfById(shelfId: Int): Flow<Shelf?> = shelvesDao.findShelfById(shelfId).map { it?.toDomainModel() }

    suspend fun saveShelf(shelf: Shelf) = shelvesDao.saveShelf(shelf.toEntity())

    suspend fun deleteShelf(shelfId: Int) = shelvesDao.deleteShelf(shelfId)
}

private fun ShelfEntity.toDomainModel(): Shelf = Shelf(
    shelfId = shelfId,
    name = name,
    isRemovable = isRemovable,
)

private fun Shelf.toEntity(): ShelfEntity = ShelfEntity(
    shelfId = shelfId,
    name = name,
    isRemovable = isRemovable,
)
