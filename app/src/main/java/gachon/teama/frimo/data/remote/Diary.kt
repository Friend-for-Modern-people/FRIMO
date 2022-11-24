package gachon.teama.frimo.data.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

// Todo: 이미지, 키워드, 댓글 추가 방법 알아볼 것
@Entity
data class Diary(
    @PrimaryKey val id: Int, // diary 구분자
    val title: String, // diary 재목
    val content: String, // diary 내용
    val created: String, // diary 작성 날짜
    val sentiment: String, // diary 대표 감정
)
