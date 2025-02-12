package com.diego.matesanz.arcaneum.ui.common.components.addToShelfButton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.domain.Shelf
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.DROPDOWN_SHELVES_ENTER_ANIMATION_DURATION
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.DROPDOWN_SHELVES_EXIT_ANIMATION_DURATION
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.DROPDOWN_SHELVES_EXIT_FADE_ANIMATION_DURATION
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.SHELF_CHECK_ENTER_ANIMATION_DURATION
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.SHELF_CHECK_EXIT_ANIMATION_DURATION
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.SHELF_CHECK_EXIT_FADE_ANIMATION_DURATION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownAddToShelfButton(
    shelves: List<Shelf>,
    selectedShelfId: Int,
    onShelfSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainer),
    ) {
        val state = rememberAddToShelfButtonState(shelves, selectedShelfId, onShelfSelected)
        DropdownShelvesList(state)
        ShelfButton(state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAddToShelfButton(
    shelves: List<Shelf>,
    selectedShelfId: Int,
    onShelfSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainer),
    ) {
        val state = rememberAddToShelfButtonState(shelves, selectedShelfId, onShelfSelected)
        ModalShelvesList(state = state)
        ShelfButton(state = state)
    }
}

@Composable
private fun DropdownShelvesList(
    state: AddToShelfButtonState,
) {
    AnimatedVisibility(
        visible = state.optionExpanded,
        enter = expandVertically(tween(DROPDOWN_SHELVES_ENTER_ANIMATION_DURATION)) + fadeIn(),
        exit = shrinkVertically(tween(DROPDOWN_SHELVES_EXIT_ANIMATION_DURATION)) +
                fadeOut(tween(DROPDOWN_SHELVES_EXIT_FADE_ANIMATION_DURATION)),
    ) {
        ShelvesList(state = state)
    }
}

@Composable
private fun ShelvesList(
    state: AddToShelfButtonState,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        state.shelves.forEachIndexed { index, shelf ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(
                        onClick = { state.selectSelf(shelf) },
                        role = Role.Button,
                    )
                    .padding(16.dp)
            ) {
                val SHELF_CHECK_EXIT_FADE_ANIMATION_DURATION = 0
                AnimatedVisibility(
                    visible = state.selectedShelf == shelf,
                    enter = expandHorizontally(tween(SHELF_CHECK_ENTER_ANIMATION_DURATION)) + fadeIn(),
                    exit = shrinkHorizontally(tween(SHELF_CHECK_EXIT_ANIMATION_DURATION)) +
                            fadeOut(tween(SHELF_CHECK_EXIT_FADE_ANIMATION_DURATION)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Icon check",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Text(
                    text = shelf.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    textDecoration = state.getTextDecoration(shelf),
                )
            }
            if (index != state.shelves.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun ShelfButton(
    state: AddToShelfButtonState,
) {
    state.shownShelf?.let { shelf ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(state.getButtonColor())
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clickable(
                        onClick = { state.selectSelf(shelf) },
                        role = Role.Button,
                    )
                    .padding(16.dp)
                    .weight(1f)
                    .animateContentSize(),
            ) {
                AnimatedVisibility(
                    visible = state.isSaved,
                    enter = expandHorizontally(tween(SHELF_CHECK_ENTER_ANIMATION_DURATION)) + fadeIn(),
                    exit = shrinkHorizontally(tween(SHELF_CHECK_EXIT_ANIMATION_DURATION)) +
                            fadeOut(tween(SHELF_CHECK_EXIT_FADE_ANIMATION_DURATION)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Icon check",
                        tint = state.getButtonContentColor(),
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .animateContentSize(),
                    text = shelf.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = state.getButtonContentColor(),
                )
            }
            VerticalDivider(modifier = Modifier.padding(vertical = 8.dp))
            IconButton(onClick = { state.optionExpanded = !state.optionExpanded }) {
                Icon(
                    imageVector = state.getExpandIcon(),
                    contentDescription = "Expand shelves",
                    tint = state.getButtonContentColor(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModalShelvesList(
    state: AddToShelfButtonState,
) {
    if (state.optionExpanded) {
        ModalBottomSheet(
            onDismissRequest = { state.optionExpanded = false },
            sheetState = state.sheetState,
        ) {
            ShelvesList(state = state)
        }
    }
}

@Composable
@Preview
private fun DropdownAddToShelfButtonPreview() {
    DropdownAddToShelfButton(
        shelves = listOf(
            Shelf(1, "Want to Read"),
            Shelf(2, "Currently Reading"),
            Shelf(3, "Read"),
        ),
        selectedShelfId = -1,
        onShelfSelected = {},
    )
}

@Composable
@Preview
private fun ModalAddToShelfButtonPreview() {
    ModalAddToShelfButton(
        shelves = listOf(
            Shelf(1, "Want to Read"),
            Shelf(2, "Currently Reading"),
            Shelf(3, "Read"),
        ),
        selectedShelfId = -1,
        onShelfSelected = {},
    )
}
