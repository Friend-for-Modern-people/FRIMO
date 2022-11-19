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

    @Query("SELECT * FROM Friend") // Get all friends information
    fun getFriendList(): List<Friend>

    @Query("SELECT * FROM Friend WHERE id = :id") // Get specific friend information
    fun getFriend(id: Int): Friend
}