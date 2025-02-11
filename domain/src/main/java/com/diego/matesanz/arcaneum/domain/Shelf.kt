package com.diego.matesanz.arcaneum.domain

data class Shelf(
    val shelfId: Int = 0,
    val name: String,
    val isRemovable: Boolean = true,
)
