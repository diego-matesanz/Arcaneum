package com.diego.matesanz.arcaneum.usecases

import com.diego.matesanz.arcaneum.data.repositories.BooksRepository
import com.diego.matesanz.arcaneum.domain.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindBooksBySearchTextUseCase @Inject constructor(
    private val booksRepository: BooksRepository,
) {
    operator fun invoke(search: String): Flow<List<Book>> =
        booksRepository.findBooksBySearchText(search)
}
