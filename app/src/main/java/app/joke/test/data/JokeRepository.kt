package app.joke.test.data

interface JokeRepository {
    suspend fun getJoke(): String
}
