package app.joke.test.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.joke.test.data.JokeDao
import app.joke.test.data.JokeEntity
import app.joke.test.domain.JokeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val jokeUseCase: JokeUseCase,
    private val jokeDao: JokeDao
) : ViewModel() {

    private val _jokes = MutableStateFlow<List<JokeEntity>>(emptyList())
    val jokes: StateFlow<List<JokeEntity>> get() = _jokes

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            jokeDao.getLatestJokes().collect {
                _jokes.emit(it)
            }
        }
        viewModelScope.launch {
            jokeDao.deleteOldJokes()
        }
    }

    fun fetchJoke() {
        viewModelScope.launch {
            try {
                val joke = jokeUseCase.getJoke()
                jokeDao.insertJoke(JokeEntity(joke = joke))
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
            }
        }
    }

    fun retryFetchJoke() {
        _error.value = null
        fetchJoke()
    }
}
