package com.diego.matesanz.arcaneum.data.datasource.database

object DatabaseConstants {

    // Database
    const val DATABASE_NAME = "arcaneum-db"

    // Shelf table
    const val SHELF_TABLE = "Shelf"
    const val SHELF_NAME_COLUMN = "name"
    const val SHELF_ID_COLUMN = "shelfId"
    const val IS_REMOVABLE_COLUMN = "isRemovable"
    const val WANT_TO_READ_SHELF_ID = 0
    const val CURRENTLY_READING_SHELF_ID = 1
    const val READ_SHELF_ID = 2
}
