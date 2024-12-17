package com.diego.matesanz.arcaneum.data.datasource.database

import android.content.ContentValues
import android.content.Context
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diego.matesanz.arcaneum.R

class DatabaseCallback(
    private val context: Context,
) : RoomDatabase.Callback() {

    companion object {
        private const val SHELF_TABLE = "Shelf"
        private const val NAME_COLUMN = "name"
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        listOf(
            ContentValues().apply { put(NAME_COLUMN, context.getString(R.string.want_read_shelf)) },
            ContentValues().apply { put(NAME_COLUMN, context.getString(R.string.reading_shelf)) },
            ContentValues().apply { put(NAME_COLUMN, context.getString(R.string.read_shelf)) },
        ).forEach {
            db.insert(SHELF_TABLE, OnConflictStrategy.REPLACE, it)
        }
    }
}
