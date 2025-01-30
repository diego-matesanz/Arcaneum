package com.diego.matesanz.arcaneum.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diego.matesanz.arcaneum.data.datasource.database.entities.ShelfEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelvesDao {

    @Query("SELECT * FROM ShelfEntity")
    fun getShelves(): Flow<List<ShelfEntity>>

    @Query("SELECT * FROM ShelfEntity WHERE shelfId = :shelfId")
    fun findShelfById(shelfId: Int): Flow<ShelfEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShelf(shelf: ShelfEntity)

    @Query("DELETE FROM ShelfEntity WHERE shelfId = :shelfId")
    suspend fun deleteShelf(shelfId: Int)
}
