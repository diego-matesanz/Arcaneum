package com.example.architectcoders

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.architectcoders.ui.theme.ArchitectCodersTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitectCodersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Books") },
                                scrollBehavior = scrollBehavior,
                            )
                        },
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    ) { padding ->
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
                            columns = GridCells.Adaptive(120.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            contentPadding = padding,
                        ) {
                            items(books) { book ->
                                BookItem(book = book)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
) {

}

@Composable
fun BookItem(book: Book) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3F)
                .clip(MaterialTheme.shapes.small),
            model = book.coverUrl,
            contentDescription = book.title,
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = book.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppPreview() {
    ArchitectCodersTheme {
        Surface {
            App()
        }
    }
}

