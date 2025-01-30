package com.diego.matesanz.arcaneum.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diego.matesanz.arcaneum.data.datasource.database.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM BookEntity")
    fun getSavedBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE bookId = :bookId")
    fun findSavedBookById(bookId: String): Flow<BookEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookEntity)

    @Query("DELETE FROM BookEntity WHERE bookId = :bookId")
    suspend fun deleteBook(bookId: String)
}
