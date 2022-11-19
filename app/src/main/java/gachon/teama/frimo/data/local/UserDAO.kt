package gachon.teama.frimo.data.local

import androidx.room.*
import gachon.teama.frimo.data.entities.User
import java.util.*

@Dao
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    // Get user nickname
    @Query("Select nickname from User")
    fun getNickname() : String

    // Update user nickname
    @Query("UPDATE User Set nickname = :nickname")
    fun updateNickname(nickname: String) : Void

    // Get recently chat date
    @Query("Select recently_chat_date from User")
    fun getRecentlyChatDate() : String

    // Update recently chat date
    @Query("UPDATE User Set recently_chat_date = :date")
    fun updateRecentlyChatDate(date: Date) : Void

    // Get recently chat character id
    @Query("Select recently_chat_character_id from User")
    fun getRecentlyChatCharacterId() : Int

    // Update recently chat character id
    @Query("UPDATE User Set recently_chat_character_id = :character_id")
    fun updateRecentlyChatCharacterId(character_id: Int) : Void

}