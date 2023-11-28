package app.joke.test.presentation.di

import androidx.room.Room
import app.joke.test.data.AppDatabase
import app.joke.test.data.JokeApiService
import app.joke.test.data.JokeRepository
import app.joke.test.data.JokeRepositoryImpl
import app.joke.test.domain.JokeUseCase
import app.joke.test.presentation.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<JokeApiService> { createWebService() }
    single<JokeRepository> { JokeRepositoryImpl(get()) }
    single { JokeUseCase(get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().jokeDao() }
    viewModel { MainViewModel(get(), get()) }
}

private inline fun <reified T> createWebService(): T {
    val okHttpClient = OkHttpClient.Builder().build()
    val gson = GsonBuilder().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://geek-jokes.sameerkumar.website/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(T::class.java)
}
