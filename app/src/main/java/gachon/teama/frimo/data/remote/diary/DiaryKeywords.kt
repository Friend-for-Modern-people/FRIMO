package gachon.teama.frimo.data.remote.diary

import com.google.gson.annotations.SerializedName

data class DiaryKeywords(
    @SerializedName("diaryPk") val diaryId: Long, // 다이어리 id
    @SerializedName("tagContent") val word: String, // 내가 쓴 단어들
    @SerializedName("sentimentTag") val sentiment: String // 감정
)