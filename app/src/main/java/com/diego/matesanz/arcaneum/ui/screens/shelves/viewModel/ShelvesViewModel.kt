package com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.data.ShelvesRepository
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ShelvesAction {
    data class AddShelf(val name: String) : ShelvesAction
    data class RemoveShelf(val shelf: Shelf) : ShelvesAction
}

class ShelvesViewModel(
    booksRepository: BooksRepository,
    private val shelvesRepository: ShelvesRepository,
) : ViewModel() {

    val state: StateFlow<Result<Map<Shelf, List<Book>>>> = booksRepository.booksByShelf
        .stateAsResultIn(viewModelScope)

    fun onAction(action: ShelvesAction) {
        when (action) {
            is ShelvesAction.AddShelf -> addShelf(action.name)
            is ShelvesAction.RemoveShelf -> removeShelf(action.shelf)
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
