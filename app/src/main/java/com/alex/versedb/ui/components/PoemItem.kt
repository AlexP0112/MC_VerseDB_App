package com.alex.versedb.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alex.versedb.domain.model.Poem
import com.alex.versedb.ui.viewmodel.PoetryViewModel

@Composable
fun PoemItem(poem: Poem, viewModel: PoetryViewModel) {
    val isFavorite by viewModel.isFavorite(poem).collectAsState(initial = false)

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = poem.title, fontWeight = FontWeight.Bold)
            Text(text = poem.author)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = poem.lines.joinToString("\n"))
            Button(onClick = { viewModel.toggleFavorite(poem) }) {
                Text(if (isFavorite) "Unfavorite" else "Favorite")
            }
        }
    }
}
