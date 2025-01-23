package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface HomeAction {
    data class Search(val search: String) : HomeAction
    data class Bookmarked(val shelfId: Int, val book: Book) : HomeAction
}

class HomeViewModel(
    shelvesRepository: ShelvesRepository,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    private var search = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<UiState> = search
        .filter { it.isNotBlank() }
        .flatMapLatest { search -> booksRepository.findBooksBySearchText(search) }
        .combine(shelvesRepository.shelves) { books, shelves -> Pair(books, shelves) }
        .map { UiState(books = it.first, shelves = it.second) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(isLoading = true),
        )

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
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
        this.search.value = search
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
