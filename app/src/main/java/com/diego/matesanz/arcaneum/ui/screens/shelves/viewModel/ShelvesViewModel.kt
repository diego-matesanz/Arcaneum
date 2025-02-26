package com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import com.diego.matesanz.arcaneum.usecases.CreateShelfUseCase
import com.diego.matesanz.arcaneum.usecases.GetBooksByShelfUseCase
import com.diego.matesanz.arcaneum.usecases.RemoveShelfUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

sealed interface ShelvesAction {
    data class AddShelf(val name: String) : ShelvesAction
    data class RemoveShelf(val shelf: Shelf) : ShelvesAction
}

@KoinViewModel
class ShelvesViewModel(
    getBooksByShelfUseCase: GetBooksByShelfUseCase,
    private val createShelfUseCase: CreateShelfUseCase,
    private val removeShelfUseCase: RemoveShelfUseCase,
) : ViewModel() {

    val state: StateFlow<Result<Map<Shelf, List<Book>>>> = getBooksByShelfUseCase()
        .stateAsResultIn(viewModelScope)

    fun onAction(action: ShelvesAction) {
        when (action) {
            is ShelvesAction.AddShelf -> addShelf(action.name)
            is ShelvesAction.RemoveShelf -> removeShelf(action.shelf)
        }
    }

    private fun addShelf(name: String) {
        viewModelScope.launch {
            createShelfUseCase(Shelf(name = name))
        }
    }

    private fun removeShelf(shelf: Shelf) {
        viewModelScope.launch {
            removeShelfUseCase(shelf)
        }
    }
}
