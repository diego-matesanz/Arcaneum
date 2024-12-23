package com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface ShelvesAction {
    data class AddShelf(val name: String) : ShelvesAction
    data class RemoveShelf(val shelf: Shelf) : ShelvesAction
}

class ShelvesViewModel(
    private val shelvesRepository: ShelvesRepository,
    private val booksRepository: BooksRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val booksByShelf: Map<Shelf, List<Book>> = emptyMap(),
        val isLoading: Boolean = false,
    )

    init {
        getBooksByShelf()
    }

    fun onAction(action: ShelvesAction) {
        when (action) {
            is ShelvesAction.AddShelf -> addShelf(action.name)
            is ShelvesAction.RemoveShelf -> removeShelf(action.shelf)
        }
    }

    private fun getBooksByShelf() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            booksRepository.booksByShelf.collect { booksByShelf ->
                _state.update { it.copy(isLoading = false, booksByShelf = booksByShelf) }
            }
        }
    }

    private fun addShelf(name: String) {
        viewModelScope.launch {
            shelvesRepository.saveShelf(Shelf(name = name))
        }
    }

    private fun removeShelf(shelf: Shelf) {
        viewModelScope.launch {
            shelvesRepository.deleteShelf(shelf)
        }
    }
}
