package gachon.teama.frimo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var nickname: String, // User nickname
    var recently_chat_character_id: Int, // 최근 대화 캐릭터
    var recently_chat_date: String // 최근 대화 날짜
)