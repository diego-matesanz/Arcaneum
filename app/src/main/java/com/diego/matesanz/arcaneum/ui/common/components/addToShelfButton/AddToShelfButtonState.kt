package com.diego.matesanz.arcaneum.ui.common.components.addToShelfButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import com.diego.matesanz.arcaneum.data.Shelf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddToShelfButtonState(
    val shelves: List<Shelf>,
    val sheetState: SheetState,
    val scope: CoroutineScope,
    private val selectedShelfId: Int,
    private val onShelfSelected: (Int) -> Unit,
) {
    var optionExpanded by mutableStateOf(false)
    var selectedShelf by mutableStateOf(shelves.find { it.shelfId == selectedShelfId })
    var isSaved by mutableStateOf(selectedShelf != null)
    var shownShelf by mutableStateOf(selectedShelf ?: shelves.firstOrNull())

    fun selectSelf(shelf: Shelf) {
        selectedShelf = if (selectedShelf == shelf) null else shelf
        isSaved = selectedShelf != null
        shownShelf = selectedShelf ?: shelves.firstOrNull()
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                optionExpanded = false
            }
        }
        onShelfSelected(shelf.shelfId)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAddToShelfButtonState(
    shelves: List<Shelf>,
    selectedShelfId: Int,
    onShelfSelected: (Int) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    scope: CoroutineScope = rememberCoroutineScope(),
): AddToShelfButtonState {
    return remember {
        AddToShelfButtonState(
            shelves = shelves,
            selectedShelfId = selectedShelfId,
            onShelfSelected = onShelfSelected,
            sheetState = sheetState,
            scope = scope,
        )
    }
}
