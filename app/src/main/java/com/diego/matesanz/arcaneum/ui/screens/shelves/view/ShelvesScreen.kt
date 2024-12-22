package com.diego.matesanz.arcaneum.ui.screens.shelves.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.data.Shelf
import com.diego.matesanz.arcaneum.ui.common.components.AddShelfDialog
import com.diego.matesanz.arcaneum.ui.common.components.CustomAsyncImage
import com.diego.matesanz.arcaneum.ui.common.components.RemoveShelfDialog
import com.diego.matesanz.arcaneum.ui.common.components.TopBar
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel.ShelvesAction
import com.diego.matesanz.arcaneum.ui.screens.shelves.viewModel.ShelvesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelvesScreen(
    onShelfClick: (Int) -> Unit,
    viewModel: ShelvesViewModel,
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var showAddShelfDialog by remember { mutableStateOf(false) }
    var showRemoveShelfDialog by remember { mutableStateOf(false) }
    var shelfToRemove by remember { mutableStateOf<Shelf?>(null) }

    Screen(
        contentDescription = "Shelves screen content description",
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.shelves_screen_title),
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddShelfDialog = true },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create shelf icon",
                    )
                }
            }
        ) { padding ->
            ShelvesContent(
                booksByShelf = state.booksByShelf,
                onShelfClick = onShelfClick,
                onDeleteClick = {
                    shelfToRemove = it
                    showRemoveShelfDialog = true
                },
                modifier = Modifier.padding(padding),
            )
            if (showAddShelfDialog) {
                AddShelfDialog(
                    onDismissRequest = { showAddShelfDialog = false },
                    onConfirmation = { viewModel.onAction(ShelvesAction.AddShelf(it)) },
                )
            }
            if (showRemoveShelfDialog) {
                RemoveShelfDialog(
                    onDismissRequest = {
                        shelfToRemove = null
                        showRemoveShelfDialog = false
                    },
                    onConfirmation = {
                        shelfToRemove?.let { viewModel.onAction(ShelvesAction.RemoveShelf(it)) }
                        showRemoveShelfDialog = false
                    },
                )
            }
        }
    }
}

@Composable
private fun ShelvesContent(
    booksByShelf: Map<Shelf, List<Book>>,
    onShelfClick: (Int) -> Unit,
    onDeleteClick: (Shelf) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(booksByShelf.keys.toList()) { shelf ->
            ShelfItem(
                shelf = shelf,
                books = booksByShelf[shelf],
                onShelfClick = onShelfClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun ShelfItem(
    shelf: Shelf,
    books: List<Book>?,
    onShelfClick: (Int) -> Unit,
    onDeleteClick: (Shelf) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onShelfClick(shelf.shelfId) }
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            BooksCovers(books = books ?: emptyList())
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = shelf.name,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(R.string.books_in_shelf, books?.size ?: 0),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Chevron right icon",
            )
        }
        if (shelf.isRemovable) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete shelf icon",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDeleteClick(shelf) },
            )
        }
    }
}

@Composable
private fun BooksCovers(
    books: List<Book>,
    modifier: Modifier = Modifier,
) {
    val imageSize = 90.dp
    val overlap = 15.dp
    val translation = 15

    val nFirstBooks = books.take(3)

    Box(modifier = modifier) {
        if (nFirstBooks.isNotEmpty()) {
            nFirstBooks.forEachIndexed { index, book ->
                CustomAsyncImage(
                    model = book.coverImage,
                    contentDescription = stringResource(
                        R.string.book_cover_content_accessibility_description,
                        book.title
                    ),
                    modifier = Modifier
                        .height(if (index == 0) imageSize else imageSize - (overlap * index))
                        .aspectRatio(BOOK_ASPECT_RATIO)
                        .offset(
                            x = if (index > 0) (index * translation * 1.25).dp else 0.dp,
                            y = if (index > 0) (index * translation).dp else 0.dp,
                        )
                        .zIndex(nFirstBooks.size - index.toFloat()),
                )
            }
        } else {
            Image(
                modifier = Modifier
                    .height(imageSize)
                    .aspectRatio(BOOK_ASPECT_RATIO),
                painter = painterResource(R.drawable.ic_empty_shelf),
                contentDescription = stringResource(id = R.string.empty_search),
            )
        }
    }
}
