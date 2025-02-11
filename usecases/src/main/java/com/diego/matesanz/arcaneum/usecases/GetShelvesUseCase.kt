package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow

class GetShelvesUseCase(private val shelvesRepository: ShelvesRepository) {
    operator fun invoke(): Flow<List<Shelf>> = shelvesRepository.shelves
}
