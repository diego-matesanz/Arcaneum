package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface ShelfDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : ShelfDetailAction
}

class ShelfDetailViewModel(
    shelfId: Int,
    shelvesRepository: ShelvesRepository,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    val state: StateFlow<Result<Pair<List<Book>, List<Shelf>>>> =
        combine(
            booksRepository.findSavedBooksByShelfId(shelfId),
            shelvesRepository.shelves,
        ) { books, shelves -> Pair(books, shelves) }
            .stateAsResultIn(viewModelScope)

    fun onAction(action: ShelfDetailAction) {
        when (action) {
            is ShelfDetailAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
        }
    }

    private fun onBookmarked(shelfId: Int, book: Book) {
        viewModelScope.launch {
            if (book.shelfId == shelfId) {
                booksRepository.deleteBook(book.bookId)
            } else {
                booksRepository.saveBook(book.copy(shelfId = shelfId))
            }
        }
    }
}
