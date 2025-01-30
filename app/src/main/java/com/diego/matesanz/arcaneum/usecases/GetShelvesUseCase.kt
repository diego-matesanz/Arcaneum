package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.flow.Flow

class GetShelvesUseCase(private val shelvesRepository: ShelvesRepository) {
    operator fun invoke(): Flow<List<Shelf>> = shelvesRepository.shelves
}
