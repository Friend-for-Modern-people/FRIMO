package gachon.teama.frimo.data.local

import androidx.room.*
import gachon.teama.frimo.data.entities.Friend

@Dao
interface FriendDAO {
    @Insert
    fun insert(friend: Friend)

    @Update
    fun update(friend: Friend)

    @Delete
    fun delete(friend: Friend)

    @Query("SELECT * FROM Friend") // 테이블의 모든 캐릭터를 가져와라
    fun getAll(): List<Friend>
}