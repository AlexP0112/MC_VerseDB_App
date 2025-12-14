package com.alex.versedb.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.versedb.ui.components.PoemItem
import com.alex.versedb.ui.viewmodel.PoetryViewModel

@Composable
fun FavoritesScreen(viewModel: PoetryViewModel = hiltViewModel()) {
    val favoritePoems by viewModel.favoritePoems.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(favoritePoems) {
            poem -> PoemItem(poem, viewModel)
        }
    }
}
