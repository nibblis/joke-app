package app.joke.test

import android.app.Application
import app.joke.test.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JokeApplication)
            modules(appModule)
        }
    }
}