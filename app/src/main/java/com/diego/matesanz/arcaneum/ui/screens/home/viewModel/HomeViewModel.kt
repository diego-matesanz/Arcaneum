package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface HomeAction {
    data class Search(val search: String) : HomeAction
    object Bookmarked : HomeAction
    object MessageShown : HomeAction
}

class HomeViewModel(
    private val repository: BooksRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
        val searchText: String = "",
        val isError: Boolean = false,
        val message: String? = null,
    )

    init {
        onAction(HomeAction.Search("Harry Potter"))
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Search -> findBooksBySearch(action.search)
            HomeAction.Bookmarked -> onBookmarked()
            HomeAction.MessageShown -> onMessageShown()
        }
    }

    private fun findBooksBySearch(search: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, searchText = search, isError = false) }
                _state.update {
                    it.copy(
                        isLoading = false,
                        books = repository.findBooksBySearchText(search)
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    private fun onBookmarked() {
        _state.update { it.copy(message = "Bookmarked") }
    }

    private fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}
