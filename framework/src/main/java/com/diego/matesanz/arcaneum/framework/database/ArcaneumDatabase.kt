package com.diego.matesanz.arcaneum.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diego.matesanz.arcaneum.framework.database.dao.BooksDao
import com.diego.matesanz.arcaneum.framework.database.dao.ShelvesDao
import com.diego.matesanz.arcaneum.framework.database.entities.BookEntity
import com.diego.matesanz.arcaneum.framework.database.entities.ShelfEntity

@Database(
    entities = [BookEntity::class, ShelfEntity::class],
    version = 4,
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
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Book RENAME TO BookEntity")
                database.execSQL("ALTER TABLE Shelf RENAME TO ShelfEntity")
                database.execSQL(
                    """
            CREATE TABLE BookEntity_temp (
                bookId TEXT NOT NULL PRIMARY KEY,
                shelfId INTEGER NOT NULL,
                title TEXT NOT NULL,
                authors TEXT NOT NULL,
                coverImage TEXT NOT NULL,
                pageCount INTEGER NOT NULL,
                description TEXT NOT NULL,
                language TEXT NOT NULL,
                averageRating REAL NOT NULL,
                ratingsCount INTEGER NOT NULL,
                FOREIGN KEY (shelfId) REFERENCES ShelfEntity(shelfId) ON DELETE CASCADE ON UPDATE CASCADE
            )
        """.trimIndent()
                )
                database.execSQL(
                    """
            INSERT INTO BookEntity_temp (bookId, shelfId, title, authors, coverImage, pageCount, description, language, averageRating, ratingsCount)
            SELECT bookId, shelfId, title, authors, coverImage, pageCount, description, language, averageRating, ratingsCount
            FROM BookEntity
        """.trimIndent()
                )
                database.execSQL("DROP TABLE BookEntity")
                database.execSQL("ALTER TABLE BookEntity_temp RENAME TO BookEntity")
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE INDEX index_BookEntity_shelfId ON BookEntity (shelfId)")
            }
        }
    }
}
