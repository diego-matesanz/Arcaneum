package com.diego.matesanz.arcaneum.framework.database

import android.content.ContentValues
import android.content.Context
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.diego.matesanz.arcaneum.framework.R
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.CURRENTLY_READING_SHELF_ID
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.IS_REMOVABLE_COLUMN
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.READ_SHELF_ID
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.SHELF_ID_COLUMN
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.SHELF_NAME_COLUMN
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.SHELF_TABLE
import com.diego.matesanz.arcaneum.framework.database.DatabaseConstants.WANT_TO_READ_SHELF_ID

class DatabaseCallback(
    private val context: Context,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        listOf(
            ContentValues().apply {
                put(SHELF_ID_COLUMN, WANT_TO_READ_SHELF_ID)
                put(SHELF_NAME_COLUMN, context.getString(R.string.want_read_shelf))
                put(IS_REMOVABLE_COLUMN, false)
            },
            ContentValues().apply {
                put(SHELF_ID_COLUMN, CURRENTLY_READING_SHELF_ID)
                put(SHELF_NAME_COLUMN, context.getString(R.string.reading_shelf))
                put(IS_REMOVABLE_COLUMN, false)
            },
            ContentValues().apply {
                put(SHELF_ID_COLUMN, READ_SHELF_ID)
                put(SHELF_NAME_COLUMN, context.getString(R.string.read_shelf))
                put(IS_REMOVABLE_COLUMN, false)
            },
        ).forEach {
            db.insert(SHELF_TABLE, OnConflictStrategy.REPLACE, it)
        }
    }
}
