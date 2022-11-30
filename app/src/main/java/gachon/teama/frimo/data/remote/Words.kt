package gachon.teama.frimo.data.remote

import androidx.room.PrimaryKey

data class Words(
    @PrimaryKey val diaryId : Long, // diary 구분자
    val word: String, // 내가 쓴 단어들
    val sentiment: Int // 감정
)