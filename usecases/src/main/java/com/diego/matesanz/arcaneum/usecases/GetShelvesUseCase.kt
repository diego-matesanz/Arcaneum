package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.ShelvesRepository
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShelvesUseCase @Inject constructor(
    private val shelvesRepository: ShelvesRepository,
) {
    operator fun invoke(): Flow<List<Shelf>> = shelvesRepository.shelves
}
