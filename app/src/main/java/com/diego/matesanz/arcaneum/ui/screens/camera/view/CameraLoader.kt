package com.diego.matesanz.arcaneum.ui.screens.camera.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.ui.common.utils.Constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.ui.common.components.LoadingSkeleton

@Composable
fun BookResultLoader(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 32.dp,
            )
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LoadingSkeleton(
                modifier = Modifier
                    .height(90.dp)
                    .aspectRatio(BOOK_ASPECT_RATIO),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                LoadingSkeleton(modifier = Modifier.size(width = 120.dp, height = 28.dp))
                LoadingSkeleton(modifier = Modifier.size(width = 80.dp, height = 20.dp))
            }
        }
    }
}
