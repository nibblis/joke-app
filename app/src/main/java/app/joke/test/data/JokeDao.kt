package app.joke.test.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes WHERE id IN (SELECT id FROM jokes ORDER BY id DESC LIMIT 10) ORDER BY id")
    fun getLatestJokes(): Flow<List<JokeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(jokeEntity: JokeEntity)

    @Query("DELETE FROM jokes WHERE id NOT IN (SELECT id FROM jokes ORDER BY id DESC LIMIT 10)")
    suspend fun deleteOldJokes()
}