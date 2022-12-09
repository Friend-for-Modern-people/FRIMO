package gachon.teama.frimo.data.entities

import android.os.Parcelable
import gachon.teama.frimo.retrofit.dao.User
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

// Todo: 키워드, 댓글 추가 방법 알아볼 것
@Parcelize
data class Diary(
    val id: Long, // diary 구분자
    val title: String, // diary 재목
    val content: String, // diary 내용
    val user: User, // diary 작성한 사람
    val created: LocalDateTime, // diary 작성 날짜
    val createdYear : Int,
    val createdMonth : Int,
    val sentiment: Int, // diary 대표 감정
): Parcelable
