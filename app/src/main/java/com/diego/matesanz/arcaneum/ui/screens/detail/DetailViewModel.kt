package com.diego.matesanz.arcaneum.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    object Bookmarked : DetailAction
    object MessageShown : DetailAction
    data class DominantColor(val color: Int) : DetailAction
}

class DetailViewModel(private val id: String) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    private val repository = BooksRepository()

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val dominantColor: Int = 0,
        val message: String? = null,
    )

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false, book = repository.fetchBookById(id)) }
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.Bookmarked -> onBookmarked()
            DetailAction.MessageShown -> onMessageShown()
            is DetailAction.DominantColor -> onDominantColor(action.color)
        }
    }

    private fun onDominantColor(color: Int) {
        _state.update { it.copy(dominantColor = color) }
    }

    private fun onBookmarked() {
        _state.update { it.copy(message = "Bookmarked") }
    }

    private fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}
