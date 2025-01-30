package com.diego.matesanz.arcaneum.data.datasource

import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.datasource.database.dao.ShelvesDao
import com.diego.matesanz.arcaneum.data.datasource.database.entities.ShelfEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ShelvesLocalDataSource {
    fun getShelves(): Flow<List<Shelf>>
    suspend fun saveShelf(shelf: Shelf)
    suspend fun deleteShelf(shelfId: Int)
}

class ShelvesRoomDataSource(
    private val shelvesDao: ShelvesDao,
): ShelvesLocalDataSource {

    override fun getShelves(): Flow<List<Shelf>> = shelvesDao.getShelves().map { shelves -> shelves.map { it.toDomainModel() } }

    override suspend fun saveShelf(shelf: Shelf) = shelvesDao.saveShelf(shelf.toEntity())

    override suspend fun deleteShelf(shelfId: Int) = shelvesDao.deleteShelf(shelfId)
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
