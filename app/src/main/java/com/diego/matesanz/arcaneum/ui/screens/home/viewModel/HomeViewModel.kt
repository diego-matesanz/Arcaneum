package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

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

sealed interface HomeAction {
    data class Search(val search: String) : HomeAction
    data class Bookmarked(val shelfId: Int, val book: Book) : HomeAction
}

class HomeViewModel(
    private val booksRepository: BooksRepository,
    private val shelvesRepository: ShelvesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
        val searchText: String = "",
        val isError: Boolean = false,
        val shelves: List<Shelf> = emptyList(),
    )

    init {
        onAction(HomeAction.Search("Harry Potter"))
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Search -> findBooksBySearch(action.search)
            is HomeAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
        }
    }

    private fun findBooksBySearch(search: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, searchText = search, isError = false) }
                combine(
                    booksRepository.findBooksBySearchText(search),
                    shelvesRepository.shelves,
                ) { books, shelves ->
                    _state.update {
                        it.copy(
                            shelves = shelves,
                            isLoading = false,
                            books = books,
                        )
                    }
                }.collect()
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
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
