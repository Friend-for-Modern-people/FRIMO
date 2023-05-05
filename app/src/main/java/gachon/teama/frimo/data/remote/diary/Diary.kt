package gachon.teama.frimo.data.remote.diary

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.User
import kotlinx.parcelize.Parcelize

// Todo: 키워드, 댓글 추가 방법 알아볼 것
@Parcelize
class Diary(
    @SerializedName("diaryPk") val id: Long, // diary 구분자
    @SerializedName("diaryTitle") val title: String, // diary 재목
    @SerializedName("diaryContent") val content: String, // diary 내용
    @SerializedName("user") val user: User, // diary 작성한 사람
    @SerializedName("dateCreated") val created: String, // diary 작성 날짜
    @SerializedName("dateCreatedinString") val createdString: String,
    @SerializedName("dateCreatedYear") val createdYear: Int,
    @SerializedName("dateCreatedMonth") val createdMonth: Int,
    @SerializedName("mainSent") val sentiment: Int, // diary 대표 감정
) : Parcelable {

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - None
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    fun getTextSentiment(): String {
        return when (sentiment) {
            Sentiment.Anger.intValue -> "#분노"
            Sentiment.Sadness.intValue -> "#슬픔"
            Sentiment.Anxiety.intValue -> "#불안"
            Sentiment.Wound.intValue -> "#상처"
            Sentiment.Embarrassment.intValue -> "#당황"
            Sentiment.Pleasure.intValue -> "#기쁨"
            else -> "#에러"
        }
    }

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - None
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    fun getSentimentColor(): Int {
        return when (sentiment) {
            Sentiment.Pleasure.intValue -> R.color.pleasure
            Sentiment.Sadness.intValue -> R.color.sadness
            Sentiment.Anxiety.intValue -> R.color.anxiety
            Sentiment.Wound.intValue -> R.color.wound
            Sentiment.Embarrassment.intValue -> R.color.embarrassment
            Sentiment.Anger.intValue -> R.color.anger
            else -> R.color.black
        }
    }

    enum class Sentiment(val intValue: Int, val stringValue: String) {
        Anger(0, "Anger"), Sadness(1, "Sadness"),
        Anxiety(2, "Anxiety"), Wound(3, "Hurt"),
        Embarrassment(4, "Embarrassment"), Pleasure(5, "Joy")
    }
}