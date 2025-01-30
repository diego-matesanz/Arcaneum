package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository

class RemoveShelfUseCase(private val shelvesRepository: ShelvesRepository) {
    suspend operator fun invoke(shelf: Shelf) {
        shelvesRepository.deleteShelf(shelf)
    }
}