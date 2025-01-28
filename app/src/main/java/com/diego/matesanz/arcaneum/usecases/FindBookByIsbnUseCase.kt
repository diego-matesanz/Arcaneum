package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository

class FindBookByIsbnUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(isbn: String): Book = booksRepository.findBookByIsbn(isbn)
}
