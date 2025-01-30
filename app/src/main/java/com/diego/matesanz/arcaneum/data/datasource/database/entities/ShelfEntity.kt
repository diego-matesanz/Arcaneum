package com.diego.matesanz.arcaneum.data.datasource.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShelfEntity(
    @PrimaryKey(autoGenerate = true)
    val shelfId: Int = 0,
    val name: String,
    val isRemovable: Boolean = true,
)
