package com.diego.matesanz.arcaneum.framework.database.datasource

import com.diego.matesanz.arcaneum.data.datasource.ShelvesLocalDataSource
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.framework.database.dao.ShelvesDao
import com.diego.matesanz.arcaneum.framework.database.entities.ShelfEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ShelvesRoomDataSource @Inject constructor(
    private val shelvesDao: ShelvesDao,
) : ShelvesLocalDataSource {

    override fun getShelves(): Flow<List<Shelf>> =
        shelvesDao.getShelves().map { shelves -> shelves.map { it.toDomainModel() } }

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
