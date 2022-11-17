package gachon.teama.frimo.data.local

import androidx.room.*
import gachon.teama.frimo.data.entities.User

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("UPDATE User Set recently_talk = :character_num") // Update recently talk
    fun updateRecentlyTalk(character_num: Int) : Void

    @Query("Select nickname from User")
    fun getNickname() : String

    @Query("UPDATE User Set nickname = :nickname") // Update recently talk
    fun updateNickname(nickname: String) : Void

}