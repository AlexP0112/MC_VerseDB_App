package com.alex.versedb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.versedb.ui.components.PoemItem
import com.alex.versedb.ui.viewmodel.PoemUiState
import com.alex.versedb.ui.viewmodel.PoetryViewModel
import com.alex.versedb.ui.viewmodel.SearchType

@Composable
fun SearchScreen(viewModel: PoetryViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf(SearchType.AUTHOR) }
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            RadioButton(
                selected = searchType == SearchType.AUTHOR,
                onClick = { searchType = SearchType.AUTHOR }
            )
            Text("Author", modifier = Modifier.align(Alignment.CenterVertically))
            RadioButton(
                selected = searchType == SearchType.TITLE,
                onClick = { searchType = SearchType.TITLE }
            )
            Text("Title", modifier = Modifier.align(Alignment.CenterVertically))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.searchPoems(query, searchType) }) {
            Text("Search")
        }

        when (val state = uiState) {
            is PoemUiState.Loading -> CircularProgressIndicator()
            is PoemUiState.Success -> {
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(state.poems) { poem ->
                        PoemItem(poem, viewModel)
                    }
                }
            }
            is PoemUiState.Error -> Text(state.message)
            else -> {}
        }
    }
}
