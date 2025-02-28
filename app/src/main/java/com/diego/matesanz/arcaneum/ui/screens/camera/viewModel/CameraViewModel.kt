package com.diego.matesanz.arcaneum.ui.screens.camera.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.domain.Book
import com.diego.matesanz.arcaneum.usecases.FindBookByIsbnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

sealed interface CameraAction {
    data class BookScanned(val isbn: String) : CameraAction
}

@HiltViewModel
class CameraViewModel @Inject constructor(
    findBookByIsbnUseCase: FindBookByIsbnUseCase,
) : ViewModel() {

    private val isbn = MutableStateFlow("")

    val state: StateFlow<UiState> = isbn
        .filter { isbn -> isbn.isNotBlank() }
        .transform { isbn -> emit(findBookByIsbnUseCase(isbn)) }
        .map { book -> UiState(book = book) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(isLoading = true),
        )

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val isError: Boolean = false,
    )

    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.BookScanned -> findBookByIsbn(action.isbn)
        }
    }

    private fun findBookByIsbn(isbn: String) {
        this.isbn.value = isbn
    }
}
