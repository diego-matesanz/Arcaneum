package com.example.architectcoders.ui.screens.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.architectcoders.data.Book
import com.example.architectcoders.R
import com.example.architectcoders.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    book: Book,
    onBack: () -> Unit,
    onBookmarked: (Book) -> Unit,
) {
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.go_back),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )
            }
        ) { padding ->
            Box {
                BookDetail(
                    book = book,
                    modifier = Modifier.padding(padding)
                )

                var bookSaved by remember { mutableStateOf(false) }
                FloatingActionButton(
                    onClick = {
                        bookSaved = !bookSaved
                        onBookmarked(book)
                    },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp),
                ) {
                    Icon(
                        imageVector = if (bookSaved) Icons.Filled.BookmarkAdded else Icons.Outlined.BookmarkAdd,
                        contentDescription = stringResource(id = R.string.bookmark),
                    )
                }
            }
        }
    }
}

@Composable
private fun BookDetail(
    book: Book,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box {
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val height = (scrollState.value / 3F).toInt()
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, height)
                        }
                    }
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(180.dp)
                        .aspectRatio(1 / 1.5F)
                        .clip(MaterialTheme.shapes.medium),
                    model = book.coverImage,
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop
                )
                TitleSection(
                    title = book.title,
                    author = book.author
                )
                InfoSection(
                    rating = book.rating,
                    pages = book.pages,
                    language = book.language
                )
                SynopsisSection(book.synopsis)
            }
        }
    }
}

@Composable
private fun TitleSection(
    title: String,
    author: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            text = author,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun InfoSection(
    rating: Float,
    pages: Int,
    language: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        InfoItem(
            title = stringResource(id = R.string.rating),
            description = rating.toString()
        )
        InfoItem(
            title = stringResource(id = R.string.pages),
            description = pages.toString()
        )
        InfoItem(
            title = stringResource(id = R.string.language),
            description = language
        )
    }
}

@Composable
private fun InfoItem(
    title: String,
    description: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Center,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SynopsisSection(synopsis: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.synopsis),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
        )

        var isExpanded by remember { mutableStateOf(false) }
        Text(
            modifier = Modifier.animateContentSize(),
            text = synopsis,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.Start,
            maxLines = if (isExpanded) Int.MAX_VALUE else 5,
            overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
        )
        Text(
            text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(id = R.string.read_more),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
            textAlign = TextAlign.End,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { isExpanded = !isExpanded }
        )
    }
}