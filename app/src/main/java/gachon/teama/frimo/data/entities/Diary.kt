package gachon.teama.frimo.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import gachon.teama.frimo.retrofit.dao.User
import kotlinx.parcelize.Parcelize

// Todo: 키워드, 댓글 추가 방법 알아볼 것
@Parcelize
data class Diary(
    @SerializedName("diaryPk") val id: Long, // diary 구분자
    @SerializedName("diaryTitle") val title: String, // diary 재목
    @SerializedName("diaryContent") val content: String, // diary 내용
    @SerializedName("user") val user: User, // diary 작성한 사람
    @SerializedName("dateCreated") val created: String, // diary 작성 날짜
    @SerializedName("dateCreatedinString") val createdString: String,
    @SerializedName("dateCreatedYear") val createdYear: Int,
    @SerializedName("dateCreatedMonth") val createdMonth: Int,
    @SerializedName("mainSent") val sentiment: Int, // diary 대표 감정
) : Parcelable
