package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Shelf
import javax.inject.Inject

class RemoveShelfUseCase @Inject constructor(
    private val shelvesRepository: ShelvesRepository,
) {
    suspend operator fun invoke(shelf: Shelf) {
        shelvesRepository.deleteShelf(shelf)
    }
}
