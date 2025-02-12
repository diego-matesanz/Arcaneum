package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import kotlinx.coroutines.flow.Flow

class FindBooksByShelfIdUseCase(private val booksRepository: BooksRepository) {
    operator fun invoke(shelfId: Int): Flow<List<Book>> =
        booksRepository.findSavedBooksByShelfId(shelfId)
}
