package com.diego.matesanz.arcaneum.ui.common.components.addToShelfButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import com.diego.matesanz.arcaneum.data.Shelf

class AddToShelfButtonState(
    val shelves: List<Shelf>,
    private val selectedShelfId: Int,
    private val onShelfSelected: (Shelf) -> Unit,
) {
    var optionExpanded by mutableStateOf(false)
    var selectedShelf by mutableStateOf(shelves.find { it.shelfId == selectedShelfId })
    var isSaved by mutableStateOf(selectedShelf != null)
    var shownShelf by mutableStateOf(selectedShelf ?: shelves.first())

    fun selectSelf(shelf: Shelf) {
        selectedShelf = if (selectedShelf == shelf) null else shelf
        isSaved = selectedShelf != null
        shownShelf = selectedShelf ?: shelves.first()
        optionExpanded = false
        onShelfSelected(shelf)
    }

    @Composable
    fun getButtonColor(): Color {
        return if (selectedShelf == null) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer
    }

    @Composable
    fun getButtonContentColor(): Color {
        return if (selectedShelf == null) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer
    }

    fun getExpandIcon(): ImageVector {
        return if (optionExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore
    }

    fun getTextDecoration(shelf: Shelf): TextDecoration {
        return if (selectedShelf == shelf) TextDecoration.Underline else TextDecoration.None
    }
}

@Composable
fun rememberAddToShelfButtonState(
    shelves: List<Shelf>,
    selectedShelfId: Int,
    onShelfSelected: (Shelf) -> Unit,
): AddToShelfButtonState {
    return remember {
        AddToShelfButtonState(
            shelves = shelves,
            selectedShelfId = selectedShelfId,
            onShelfSelected = onShelfSelected,
        )
    }
}
