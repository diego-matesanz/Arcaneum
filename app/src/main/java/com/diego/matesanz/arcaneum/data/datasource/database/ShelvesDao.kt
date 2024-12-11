package com.diego.matesanz.arcaneum.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diego.matesanz.arcaneum.data.Shelf
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelvesDao {

    @Query("SELECT * FROM Shelf")
    fun getShelves(): Flow<List<Shelf>>

    @Query("SELECT * FROM Shelf WHERE shelfId = :shelfId")
    fun findShelfById(shelfId: Int): Flow<Shelf?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveShelf(shelf: Shelf)

    @Query("DELETE FROM Shelf WHERE shelfId = :shelfId")
    suspend fun deleteShelf(shelfId: Int)
}
