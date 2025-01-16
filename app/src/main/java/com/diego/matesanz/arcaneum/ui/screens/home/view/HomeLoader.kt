package com.diego.matesanz.arcaneum.ui.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.constants.Constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.constants.Constants.BOOK_COUNT
import com.diego.matesanz.arcaneum.ui.common.components.LoadingSkeleton

@Composable
fun HomeLoader() {
    Column {
        repeat(BOOK_COUNT) {
            BookLoader()
            if (it < BOOK_COUNT - 1) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp))
            }
        }
    }
}

@Composable
private fun BookLoader() {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LoadingSkeleton(
            modifier = Modifier
                .height(180.dp)
                .aspectRatio(BOOK_ASPECT_RATIO),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                LoadingSkeleton(
                    modifier = Modifier.size(width = 180.dp, height = 24.dp),
                )
                LoadingSkeleton(
                    modifier = Modifier.size(width = 150.dp, height = 20.dp),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    LoadingSkeleton(
                        modifier = Modifier.size(width = 110.dp, height = 20.dp),
                    )
                    LoadingSkeleton(
                        modifier = Modifier.size(width = 120.dp, height = 20.dp),
                    )
                }
                LoadingSkeleton(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp),
                )
            }
        }
    }
}
