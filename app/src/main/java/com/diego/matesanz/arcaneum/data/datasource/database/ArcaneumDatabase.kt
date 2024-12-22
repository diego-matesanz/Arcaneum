package com.diego.matesanz.arcaneum.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.Shelf

@Database(
    entities = [Book::class, Shelf::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class ArcaneumDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun shelvesDao(): ShelvesDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Shelf ADD COLUMN isRemovable INTEGER NOT NULL DEFAULT 1")
            }
        }
    }
}
