package gachon.teama.frimo.data.remote

import com.google.gson.annotations.SerializedName

data class DiaryKeywords(
    @SerializedName("diaryPk") val diaryId: Long, // 다이어리 id
    @SerializedName("tagContent") val word: String, // 내가 쓴 단어들
    @SerializedName("sentLargeId") val sentiment: Int, // 감정 대분류
    @SerializedName("sentPK") val sentimentDetail: Int, // 감정 소분류
    @SerializedName("category") val category: String // 카테고리
)