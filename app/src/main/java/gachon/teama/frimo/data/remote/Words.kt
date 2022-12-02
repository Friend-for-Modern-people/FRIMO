package gachon.teama.frimo.data.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

// Todo: Int -> Long
@Entity
data class Words(
    @PrimaryKey val tagId: Int, // tag 구분자
    val diaryId : Int, // diary id
    val word: String, // 내가 쓴 단어들
    val sentiment: Int // 감정
)