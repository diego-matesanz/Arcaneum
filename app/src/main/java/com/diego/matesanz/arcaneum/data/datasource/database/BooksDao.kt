package com.diego.matesanz.arcaneum.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diego.matesanz.arcaneum.data.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM Book")
    fun getSavedBooks(): Flow<List<Book>>

    @Query("SELECT * FROM Book WHERE bookId = :bookId")
    fun findSavedBookById(bookId: String): Flow<Book?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: Book)

    @Query("DELETE FROM Book WHERE bookId = :bookId")
    suspend fun deleteBook(bookId: String)
}
