package com.alex.versedb.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.versedb.domain.PoetryRepository
import com.alex.versedb.domain.model.Poem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetryViewModel @Inject constructor(
    private val repository: PoetryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PoemUiState>(PoemUiState.Idle)
    val uiState: StateFlow<PoemUiState> = _uiState.asStateFlow()

    val favoritePoems = repository.getFavoritePoems()

    fun searchPoems(query: String, searchBy: SearchType) {
        viewModelScope.launch {
            _uiState.value = PoemUiState.Loading
            try {
                val poems = if (searchBy == SearchType.AUTHOR) {
                    repository.getPoemsByAuthor(query)
                } else {
                    repository.getPoemsByTitle(query)
                }
                _uiState.value = PoemUiState.Success(poems)
            } catch (e: Exception) {
                _uiState.value = PoemUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun isFavorite(poem: Poem) = repository.isFavorite(poem)

    fun toggleFavorite(poem: Poem) {
        viewModelScope.launch {
            if (repository.isFavorite(poem).first()) {
                repository.removeFromFavorites(poem)
            } else {
                repository.addToFavorites(poem)
            }
        }
    }
}

sealed class PoemUiState {
    object Idle : PoemUiState()
    object Loading : PoemUiState()
    data class Success(val poems: List<Poem>) : PoemUiState()
    data class Error(val message: String) : PoemUiState()
}

enum class SearchType {
    AUTHOR,
    TITLE
}
