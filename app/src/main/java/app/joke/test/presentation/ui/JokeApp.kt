package app.joke.test.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.joke.test.presentation.MainViewModel
import app.joke.test.presentation.ui.theme.JokeAppTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JokeApp(viewModel: MainViewModel) {
    val jokes by viewModel.jokes.collectAsState()
    val error by viewModel.error.collectAsState()

    JokeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    itemsIndexed(
                        items = jokes,
                        key = { _, item -> item.id }
                    ) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .animateItemPlacement(
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing,
                                    )
                                )
                        ) {
                            Text(
                                text = item.joke,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .animateContentSize()
                            )
                        }
                    }
                }

                error?.let { errorMessage ->
                    Snackbar(
                        action = {
                            TextButton(onClick = { viewModel.retryFetchJoke() }) {
                                Text("Retry")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(errorMessage)
                    }
                }
            }
        }
    }
    LaunchedEffect(true) {
        while (true) {
            delay(60_000)
            viewModel.fetchJoke()
        }
    }
}
