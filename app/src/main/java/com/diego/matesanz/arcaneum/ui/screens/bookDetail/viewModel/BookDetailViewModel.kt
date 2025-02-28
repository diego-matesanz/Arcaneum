package com.diego.matesanz.arcaneum.ui.screens.bookDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.usecases.FindBookByIdUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

sealed interface BookDetailAction {
    data class Bookmarked(val shelfId: Int, val book: Book) : BookDetailAction
}

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    @Named("bookId") bookId: String,
    findBookByIdUseCase: FindBookByIdUseCase,
    getShelvesUseCase: GetShelvesUseCase,
    private val toggleBookShelfUseCase: ToggleBookShelfUseCase,
) : ViewModel() {

    val state: StateFlow<Result<Pair<Book, List<Shelf>>>> =
        combine(
            findBookByIdUseCase(bookId),
            getShelvesUseCase(),
        ) { book, shelves -> Pair(book, shelves) }
            .stateAsResultIn(viewModelScope)

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.Bookmarked -> onBookmarked(action.shelfId, action.book)
        }
    }

    private fun onBookmarked(shelfId: Int, book: Book) {
        viewModelScope.launch {
            toggleBookShelfUseCase(shelfId, book)
        }
    }
}
