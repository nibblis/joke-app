package app.joke.test.data

class JokeRepositoryImpl(private val jokeApiService: JokeApiService) : JokeRepository {

    override suspend fun getJoke(): String {
        return jokeApiService.getJoke().joke
    }
}
