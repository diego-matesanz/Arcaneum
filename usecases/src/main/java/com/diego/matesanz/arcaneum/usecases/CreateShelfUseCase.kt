package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Shelf
import org.koin.core.annotation.Factory

@Factory
class CreateShelfUseCase(private val shelvesRepository: ShelvesRepository) {
    suspend operator fun invoke(shelf: Shelf) {
        shelvesRepository.saveShelf(shelf)
    }
}