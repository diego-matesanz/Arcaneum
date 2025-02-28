package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksByShelfUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    operator fun invoke(): Flow<Map<Shelf, List<Book>>> = booksRepository.booksByShelf
}
