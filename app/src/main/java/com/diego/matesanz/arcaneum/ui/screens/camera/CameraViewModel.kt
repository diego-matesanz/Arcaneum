package com.diego.matesanz.arcaneum.ui.screens.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CameraAction {
    data class PermissionResult(val permissionGranted: Boolean) : CameraAction
    data class BookScanned(val isbn: String) : CameraAction
}

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    private val repository = BooksRepository()

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val isError: Boolean = false,
        val permissionGranted: Boolean = false,
    )

    fun onAction(action: CameraAction) {
        when (action) {
            is CameraAction.PermissionResult -> onPermissionResult(action.permissionGranted)
            is CameraAction.BookScanned -> fetchBookByIsbn(action.isbn)
        }
    }

    private fun fetchBookByIsbn(isbn: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, isError = false) }
                _state.update {
                    it.copy(
                        isLoading = false,
                        book = repository.fetchBookByIsbn(isbn)
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false, isError = true) }
            }
        }
    }

    private fun onPermissionResult(permissionGranted: Boolean) {
        _state.update { it.copy(permissionGranted = permissionGranted) }
    }
}
