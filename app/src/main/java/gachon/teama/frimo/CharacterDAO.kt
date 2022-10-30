package gachon.teama.frimo

import androidx.room.*

@Dao
interface CharacterDAO {
    @Insert
    fun insert(character: Character)

    @Update
    fun update(character: Character)

    @Delete
    fun delete(character: Character)

    @Query("SELECT * FROM Character") // 테이블의 모든 캐릭터를 가져와라
    fun getAll(): List<Character>
}