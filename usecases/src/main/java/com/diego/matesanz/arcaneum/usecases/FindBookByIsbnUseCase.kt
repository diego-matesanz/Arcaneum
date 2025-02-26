package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import org.koin.core.annotation.Factory

@Factory
class FindBookByIsbnUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(isbn: String): Book = booksRepository.findBookByIsbn(isbn)
}
