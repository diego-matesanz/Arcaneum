package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository

class CreateShelfUseCase(private val shelvesRepository: ShelvesRepository) {
    suspend operator fun invoke(shelf: Shelf) {
        shelvesRepository.saveShelf(shelf)
    }
}