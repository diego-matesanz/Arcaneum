package com.diego.matesanz.arcaneum.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.Shelf

@Database(
    entities = [Book::class, Shelf::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class ArcaneumDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun shelvesDao(): ShelvesDao
}
