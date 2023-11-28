package app.joke.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val joke: String
)