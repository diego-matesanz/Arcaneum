package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.flow.Flow

class FindBooksByShelfIdUseCase(private val booksRepository: BooksRepository) {
    operator fun invoke(shelfId: Int): Flow<List<Book>> =
        booksRepository.findSavedBooksByShelfId(shelfId)
}
