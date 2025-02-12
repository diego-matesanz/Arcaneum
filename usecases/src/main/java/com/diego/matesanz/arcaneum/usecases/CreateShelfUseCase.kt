package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Shelf

class CreateShelfUseCase(private val shelvesRepository: ShelvesRepository) {
    suspend operator fun invoke(shelf: Shelf) {
        shelvesRepository.saveShelf(shelf)
    }
}