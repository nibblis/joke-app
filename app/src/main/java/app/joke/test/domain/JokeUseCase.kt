package app.joke.test.domain

import app.joke.test.data.JokeRepository

class JokeUseCase(private val jokeRepository: JokeRepository) {
    suspend fun getJoke(): String = jokeRepository.getJoke()
}
