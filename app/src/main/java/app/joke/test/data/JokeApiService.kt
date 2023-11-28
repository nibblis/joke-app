package app.joke.test.data

import retrofit2.http.GET

interface JokeApiService {
    @GET("api?format=json")
    suspend fun getJoke(): JokeResponse
}
