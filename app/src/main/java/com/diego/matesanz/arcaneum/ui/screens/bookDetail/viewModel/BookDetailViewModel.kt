package com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel

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

sealed interface BookDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : BookDetailAction
}

class BookDetailViewModel(
    bookId: String,
    shelvesRepository: ShelvesRepository,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    val state: StateFlow<UiState> =
        combine(
            booksRepository.findBookById(bookId),
            shelvesRepository.shelves
        ) { book, shelves -> Pair(book, shelves) }
            .map { UiState(book = it.first, shelves = it.second) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState(isLoading = true),
            )

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val shelves: List<Shelf> = emptyList(),
    )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
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
