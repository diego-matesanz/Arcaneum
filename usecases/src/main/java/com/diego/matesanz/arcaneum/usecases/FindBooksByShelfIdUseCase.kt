package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindBooksByShelfIdUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    operator fun invoke(shelfId: Int): Flow<List<Book>> =
        booksRepository.findSavedBooksByShelfId(shelfId)
}
