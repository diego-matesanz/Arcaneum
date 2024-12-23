package com.diego.matesanz.arcaneum.ui.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.R
import com.diego.matesanz.arcaneum.data.Book
import com.diego.matesanz.arcaneum.ui.common.components.TopBar
import com.diego.matesanz.arcaneum.ui.common.components.books.booksList.BookItem
import com.diego.matesanz.arcaneum.ui.screens.Screen
import com.diego.matesanz.arcaneum.ui.screens.home.viewModel.HomeAction
import com.diego.matesanz.arcaneum.ui.screens.home.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBookClick: (Book) -> Unit,
    onCamClick: () -> Unit,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen(
        contentDescription = stringResource(id = R.string.home_screen_accessibility_description),
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.app_name),
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->
            HomeContent(
                state = state,
                onBookClick = onBookClick,
                onCamClick = onCamClick,
                onBookmarked = { shelfId, book ->
                    viewModel.onAction(HomeAction.Bookmarked(shelfId, book))
                },
                onSearch = { viewModel.onAction(HomeAction.Search(it)) },
                contentPadding = padding,
            )
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeViewModel.UiState,
    onBookClick: (Book) -> Unit,
    onBookmarked: (Int, Book) -> Unit,
    onCamClick: () -> Unit,
    onSearch: (String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = contentPadding,
    ) {
        item {
            SearchBar(
                searchText = state.searchText,
                onCamClick = onCamClick,
                onSearch = onSearch,
            )
        }
        when {
            state.isLoading -> item { HomeLoader() }
            state.isError -> item { HomeError() }
            state.books.isEmpty() && state.searchText.isEmpty() -> item { HomeEmpty() }
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

@Composable
private fun SearchBar(
    searchText: String,
    onCamClick: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var search by remember { mutableStateOf(searchText) }
    TextField(
        value = search,
        onValueChange = { search = it },
        label = { Text(text = stringResource(id = R.string.search_label)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(search)
                localFocusManager.clearFocus()
                keyboardController?.hide()
            },
        ),
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                onSearch(search)
                localFocusManager.clearFocus()
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_action_accessibility_description),
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = onCamClick) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = stringResource(id = R.string.open_camera_action_accessibility_description),
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
    )
}
