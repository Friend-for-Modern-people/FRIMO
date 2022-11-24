package gachon.teama.frimo.data.local

import androidx.room.*
import gachon.teama.frimo.data.remote.Diary

@Dao
interface DiaryDAO {

    @Insert
    fun insert(diary: Diary)

    @Update
    fun update(diary: Diary)

    @Delete
    fun delete(diary: Diary)

    // Get all diary
    @Query("SELECT * FROM Diary")
    fun getDiaryList(): List<Diary>

    // Delete all diary
    @Query("DELETE FROM Diary")
    fun deleteAllDiary(): Void

    // Get specific diary
    @Query("SELECT * FROM Diary WHERE id = :id")
    fun getDiary(id: Int): Diary

    // Get diary count
    @Query("SELECT COUNT(*) FROM Diary")
    fun getDiaryCount(): Int

}