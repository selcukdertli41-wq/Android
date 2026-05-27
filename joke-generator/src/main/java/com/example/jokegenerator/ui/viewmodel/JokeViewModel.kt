package com.example.jokegenerator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jokegenerator.data.model.Joke
import com.example.jokegenerator.data.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {
    private val _jokeState = MutableLiveData<JokeState>(JokeState.Idle)
    val jokeState: LiveData<JokeState> = _jokeState

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _selectedCategory = MutableLiveData<String>("Any")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _safeMode = MutableLiveData<Boolean>(false)
    val safeMode: LiveData<Boolean> = _safeMode

    init {
        loadCategories()
    }

    fun getRandomJoke() {
        _jokeState.value = JokeState.Loading
        viewModelScope.launch {
            val result = if (_selectedCategory.value == "Any") {
                repository.getRandomJoke(_safeMode.value ?: false)
            } else {
                repository.getRandomJokeByCategory(
                    _selectedCategory.value ?: "Any",
                    _safeMode.value ?: false
                )
            }

            result.onSuccess { joke ->
                _jokeState.value = JokeState.Success(joke)
            }.onFailure { error ->
                _jokeState.value = JokeState.Error(error.message ?: "Unknown error occurred")
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getAvailableCategories()
                .onSuccess { cats ->
                    _categories.value = listOf("Any") + cats
                }
                .onFailure { error ->
                    _jokeState.value = JokeState.Error("Failed to load categories: ${error.message}")
                }
        }
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSafeMode(enabled: Boolean) {
        _safeMode.value = enabled
    }
}

seal class JokeState {
    object Idle : JokeState()
    object Loading : JokeState()
    data class Success(val joke: Joke) : JokeState()
    data class Error(val message: String) : JokeState()
}

class JokeViewModelFactory(private val repository: JokeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JokeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
