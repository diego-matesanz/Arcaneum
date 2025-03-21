package com.diego.matesanz.arcaneum.ui.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Result
import com.diego.matesanz.arcaneum.data.stateAsResultIn
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.usecases.FindBooksBySearchTextUseCase
import com.diego.matesanz.arcaneum.usecases.GetShelvesUseCase
import com.diego.matesanz.arcaneum.usecases.ToggleBookShelfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeAction {
    data class Search(val search: String) : HomeAction
    data class Bookmarked(val shelfId: Int, val book: Book) : HomeAction
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val findBooksBySearchTextUseCase: FindBooksBySearchTextUseCase,
    private val getShelvesUseCase: GetShelvesUseCase,
    private val toggleBookShelfUseCase: ToggleBookShelfUseCase,
) : ViewModel() {

    private var search = MutableStateFlow("")

    val state: StateFlow<Result<Pair<List<Book>, List<Shelf>>>>
        get() = search
        .filter { it.isNotBlank() }
        .flatMapLatest { search -> findBooksBySearchTextUseCase(search) }
        .combine(getShelvesUseCase()) { books, shelves -> Pair(books, shelves) }
        .stateAsResultIn(viewModelScope)

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
            toggleBookShelfUseCase(shelfId, book)
        }
    }
}
