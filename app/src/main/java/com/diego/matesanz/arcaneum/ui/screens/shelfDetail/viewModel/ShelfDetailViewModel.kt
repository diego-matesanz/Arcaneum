package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ShelfDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : ShelfDetailAction
}

class ShelfDetailViewModel(
    shelfId: Int,
    shelvesRepository: ShelvesRepository,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    val state: StateFlow<UiState> = combine(
        booksRepository.findSavedBooksByShelfId(shelfId),
        shelvesRepository.shelves,
    ) { books, shelves -> Pair(books, shelves) }
        .map {
            UiState(
                books = it.first ?: emptyList(),
                shelves = it.second,
                selectedShelf = it.second.find { it.shelfId == shelfId }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(isLoading = true),
        )

    data class UiState(
        val books: List<Book> = emptyList(),
        val shelves: List<Shelf> = emptyList(),
        val selectedShelf: Shelf? = null,
        val isLoading: Boolean = false,
    )

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
