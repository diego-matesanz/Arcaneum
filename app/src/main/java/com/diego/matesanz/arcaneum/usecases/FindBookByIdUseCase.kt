package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.flow.Flow

class FindBookByIdUseCase(private val booksRepository: BooksRepository) {
    operator fun invoke(bookId: String): Flow<Book> = booksRepository.findBookById(bookId)
}
