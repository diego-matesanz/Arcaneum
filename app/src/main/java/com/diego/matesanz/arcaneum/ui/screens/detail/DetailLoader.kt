package com.diego.matesanz.arcaneum.ui.screens.detail

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diego.matesanz.arcaneum.ui.common.LoadingSkeleton

@Composable
fun DetailLoader(padding: PaddingValues) {
    Box {
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Box {
                Box(
                    modifier = Modifier
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
                    LoadingSkeleton(
                        modifier = Modifier
                            .height(270.dp)
                            .aspectRatio(1 / 1.5F)
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
        repeat(3) {
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
            repeat(5) {
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
