package gachon.teama.frimo.data.entities

import com.google.gson.annotations.SerializedName

data class Words(
    @SerializedName("tagContent") val word: String, // 내가 쓴 단어들
    @SerializedName("sentLargeId") val sentiment: Int // 감정
)