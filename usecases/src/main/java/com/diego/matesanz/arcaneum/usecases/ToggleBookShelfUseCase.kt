package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book

class ToggleBookShelfUseCase(private val booksRepository: BooksRepository) {
    suspend operator fun invoke(shelfId: Int, book: Book) {
        if (book.shelfId == shelfId) {
            booksRepository.deleteBook(book.bookId)
        } else {
            booksRepository.saveBook(book.copy(shelfId = shelfId))
        }
    }
}
