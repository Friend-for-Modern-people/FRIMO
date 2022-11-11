package gachon.teama.frimo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    var recently_talk : Int // 최근 대화 캐릭터

) {
    @PrimaryKey var nickname: String = "" // User nickname
}