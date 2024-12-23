package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ShelfDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : ShelfDetailAction
}

class ShelfDetailViewModel(
    private val shelfId: Int,
    private val shelfName: String,
    private val booksRepository: BooksRepository,
    private val shelvesRepository: ShelvesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val books: List<Book> = emptyList(),
        val shelves: List<Shelf> = emptyList(),
        val shelfName: String = "",
        val isLoading: Boolean = false,
    )

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, shelfName = shelfName) }
            combine(
                booksRepository.findSavedBooksByShelfId(shelfId),
                shelvesRepository.shelves,
            ) { books, shelves ->
                _state.update {
                    it.copy(
                        books = books ?: emptyList(),
                        shelves = shelves,
                        isLoading = false
                    )
                }
            }.collect()
        }
    }

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
