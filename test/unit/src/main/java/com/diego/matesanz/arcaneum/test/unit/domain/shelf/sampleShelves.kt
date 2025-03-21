package com.diego.matesanz.arcaneum.test.unit.domain.shelf

import com.diego.matesanz.arcaneum.domain.Shelf

fun sampleShelf(shelfId: Int) = Shelf(
    shelfId = shelfId,
    name = "Sample Shelf",
)

fun sampleShelves(vararg shelfIds: Int) = shelfIds.map { sampleShelf(it) }
