package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetBooksByShelfUseCase(private val booksRepository: BooksRepository) {
    operator fun invoke(): Flow<Map<Shelf, List<Book>>> = booksRepository.booksByShelf
}
