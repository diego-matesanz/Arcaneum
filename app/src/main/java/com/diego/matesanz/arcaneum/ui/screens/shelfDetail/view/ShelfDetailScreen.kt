package com.diego.matesanz.arcaneum.ui.screens.shelfDetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.components.NavigationBackTopBar
import com.diego.matesanz.arcaneum.ui.common.components.books.booksList.BookItem
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.diego.matesanz.arcaneum.ui.screens.home.view.HomeLoader
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel.ShelfDetailAction
import com.diego.matesanz.arcaneum.ui.screens.shelfDetail.viewModel.ShelfDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelfDetailScreen(
    onBookClick: (Book) -> Unit,
    onBack: () -> Unit,
    viewModel: ShelfDetailViewModel,
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen(
        contentDescription = "Shelf detail screen content description",
    ) {
        Scaffold(
            topBar = {
                NavigationBackTopBar(
                    title = state.selectedShelf?.name ?: "",
                    scrollBehavior = scrollBehavior,
                    onBack = onBack,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->
            ShelfDetailContent(
                state = state,
                onBookClick = onBookClick,
                onBookmarked = { shelfId, book ->
                    viewModel.onAction(ShelfDetailAction.Bookmarked(shelfId, book))
                },
                contentPadding = padding,
            )
        }
    }
}

@Composable
private fun ShelfDetailContent(
    state: ShelfDetailViewModel.UiState,
    onBookClick: (Book) -> Unit,
    onBookmarked: (Int, Book) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(
            top = 24.dp + contentPadding.calculateTopPadding(),
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = contentPadding.calculateBottomPadding(),
        ),
    ) {
        when {
            state.isLoading -> item { HomeLoader() }
            else -> {
                itemsIndexed(state.books) { index, book ->
                    BookItem(
                        book = book,
                        shelves = state.shelves,
                        onClick = onBookClick,
                        onBookmarked = onBookmarked
                    )
                    if (index < state.books.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .padding(horizontal = 16.dp),
                        )
                    }
                }
            }
        }
    }
}
