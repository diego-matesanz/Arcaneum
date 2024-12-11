package com.diego.matesanz.arcaneum.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shelf(
    @PrimaryKey(autoGenerate = true)
    val shelfId: Int,
    val name: String,
)