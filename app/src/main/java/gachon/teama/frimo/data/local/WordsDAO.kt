package gachon.teama.frimo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import gachon.teama.frimo.data.remote.Words

@Dao
interface WordsDAO {

    @Insert
    fun insert(words: Words)

    @Update
    fun update(words: Words)

    // Get diary words
    @Query("SELECT * FROM Words WHERE diaryId = :id")
    fun getWords(id: Int): List<Words>

}