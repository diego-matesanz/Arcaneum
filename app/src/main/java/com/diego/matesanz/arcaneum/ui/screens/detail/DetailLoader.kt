package com.diego.matesanz.arcaneum.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.constants.BOOK_ASPECT_RATIO
import com.diego.matesanz.arcaneum.constants.INFO_ITEM_COUNT
import com.diego.matesanz.arcaneum.constants.LINE_COUNT
import com.diego.matesanz.arcaneum.ui.common.LoadingSkeleton

@Composable
fun DetailLoader(modifier: Modifier = Modifier) {
    Box {
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Box {
                LoadingSkeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
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
                    LoadingSkeleton(
                        modifier = Modifier
                            .height(270.dp)
                            .aspectRatio(BOOK_ASPECT_RATIO)
                    )
                    TitleSectionLoader()
                    InfoSectionLoader()
                    DescriptionSectionLoader()
                }
            }
        }
        LoadingSkeleton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
                .size(56.dp)
        )
    }
}

@Composable
private fun TitleSectionLoader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LoadingSkeleton(modifier = Modifier.size(240.dp, 32.dp))
        LoadingSkeleton(modifier = Modifier.size(180.dp, 24.dp))
    }
}

@Composable
private fun InfoSectionLoader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        repeat(INFO_ITEM_COUNT) {
            InfoItemLoader()
        }
    }
}

@Composable
private fun InfoItemLoader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LoadingSkeleton(modifier = Modifier.size(80.dp, 24.dp))
        LoadingSkeleton(modifier = Modifier.size(40.dp, 24.dp))
    }
}

@Composable
private fun DescriptionSectionLoader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LoadingSkeleton(modifier = Modifier.size(100.dp, 24.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            repeat(LINE_COUNT) {
                LoadingSkeleton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
            }
        }
        LoadingSkeleton(
            modifier = Modifier
                .size(80.dp, 20.dp)
                .align(Alignment.End)
        )
    }
}
