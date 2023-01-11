package gachon.teama.frimo.base

import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import java.time.LocalDate

abstract class DiaryFragment: Fragment() {

    /**
     * @description - 현재 연도 받아오기
     * @param - None
     * @return - year(Int) : 현재 연도
     * @author - namsh1125
     */
    fun getCurrentYear(): Int {
        return LocalDate.now().year
    }

    /**
     * @description - 현재 달 받아오기
     * @param - None
     * @return - year(Int) : 현재 연도
     * @author - namsh1125
     */
    fun getCurrentMonth(): Int {
        return LocalDate.now().monthValue
    }

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    fun getColor(sentiment: Int): Int {
        return when (sentiment) {
            Sentiment.Pleasure.value -> R.color.pleasure
            Sentiment.Sadness.value -> R.color.sadness
            Sentiment.Anxiety.value -> R.color.anxiety
            Sentiment.Wound.value -> R.color.wound
            Sentiment.Embarrassment.value -> R.color.embarrassment
            Sentiment.Anger.value -> R.color.anger
            else -> R.color.black
        }
    }

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    fun getTextSentiment(sentiment: Int): String {
        return when (sentiment) {
            Sentiment.Anger.value -> "#분노"
            Sentiment.Sadness.value -> "#슬픔"
            Sentiment.Anxiety.value -> "#불안"
            Sentiment.Wound.value -> "#상처"
            Sentiment.Embarrassment.value -> "#당황"
            Sentiment.Pleasure.value -> "#기쁨"
            else -> "#에러"
        }
    }

    enum class Sentiment(val value: Int) {
        Anger(0), Sadness(1), Anxiety(2), Wound(3), Embarrassment(4), Pleasure(5)
    }
}