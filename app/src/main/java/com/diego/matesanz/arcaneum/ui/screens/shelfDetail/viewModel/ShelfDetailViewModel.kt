package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import com.diego.matesanz.arcaneum.usecases.FindBooksByShelfIdUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface ShelfDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : ShelfDetailAction
}

class ShelfDetailViewModel(
    shelfId: Int,
    findBooksByShelfIdUseCase: FindBooksByShelfIdUseCase,
    getShelvesUseCase: GetShelvesUseCase,
    private val toggleBookShelfUseCase: ToggleBookShelfUseCase,
) : ViewModel() {

    val state: StateFlow<Result<Pair<List<Book>, List<Shelf>>>> =
        combine(
            findBooksByShelfIdUseCase(shelfId),
            getShelvesUseCase(),
        ) { books, shelves -> Pair(books, shelves) }
            .stateAsResultIn(viewModelScope)

    fun onAction(action: ShelfDetailAction) {
        when (action) {
            is ShelfDetailAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
        }
    }

    private fun onBookmarked(shelfId: Int, book: Book) {
        viewModelScope.launch {
            toggleBookShelfUseCase(shelfId, book)
        }
    }
}
