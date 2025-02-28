package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import javax.inject.Inject

class FindBookByIsbnUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    suspend operator fun invoke(isbn: String): Book = booksRepository.findBookByIsbn(isbn)
}
