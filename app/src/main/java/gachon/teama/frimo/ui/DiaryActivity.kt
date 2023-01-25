package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.entities.Words
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.DiaryInterestAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.ActivityDiaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(ActivityDiaryBinding::inflate) {

    private val diaryId by lazy { intent.getLongExtra("id", 0) }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        runBlocking {
            setScreen()
        }
        setClickListener()
    }


    /**
     * @description - 서버에서 받아온 diary를 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private suspend fun setScreen() {
        setDiary()
        setKeyword()
        // Todo: (Not now) 댓글 셋팅

    }

    /**
     * @description - 서버에서 diary 정보 받아와 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private suspend fun setDiary() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        lifecycleScope.launch {
            val diary = withContext(Dispatchers.IO) {
                diaryAPI.getDiaryById(diaryId)
            }
            with(binding) {
                textviewDate.text = diary.createdString
                textviewDiaryTitle.text = diary.title
                textviewDiaryContents.text = diary.content
                textviewSentiment.text = getTextSentiment(diary.sentiment)
                textviewSentiment.background.setTint(ContextCompat.getColor(this@DiaryActivity, getSentimentColor(diary.sentiment)))
                imageViewDiary.background.setTint(ContextCompat.getColor(this@DiaryActivity, getSentimentColor(diary.sentiment)))
            }
        }
    }

    /**
     * @description - 서버에서 diary의 핵심 키워드 받아와 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private suspend fun setKeyword() {

        val retrofit = RetrofitClient.getInstance()
        val diaryInterestAPI = retrofit.create(DiaryInterestAPI::class.java)

        lifecycleScope.launch {

            val keywords = withContext(Dispatchers.IO) {
                diaryInterestAPI.getFourWord(diaryId)
            }

            if (keywords.size >= 1) {
                binding.textviewKeyword1.text = getString(R.string.set_diary_keyword, keywords[0].word)
                binding.textviewKeyword1.visibility = View.VISIBLE
            }

            if (keywords.size >= 2) {
                binding.textviewKeyword2.text = getString(R.string.set_diary_keyword, keywords[1].word)
                binding.textviewKeyword2.visibility = View.VISIBLE
            }

            if (keywords.size >= 3) {
                binding.textviewKeyword3.text = getString(R.string.set_diary_keyword, keywords[2].word)
                binding.textviewKeyword3.visibility = View.VISIBLE
            }

            if (keywords.size >= 4) {
                binding.textviewKeyword4.text = getString(R.string.set_diary_keyword, keywords[3].word)
                binding.textviewKeyword4.visibility = View.VISIBLE
            }
        }
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // Set back button click listener
        binding.buttonBack.setOnClickListener {
            finish()
        }

        // Set share button click listener
        binding.buttonShare.setOnClickListener {
            // Todo: (Not now) 일기 공유하기 기능 추가
            showToast("추후 업데이트 예정입니다 :)")
        }

        // Set detail button click listener
        binding.buttonDetail.setOnClickListener {
            showPopupwindow(it)
        }

    }

    /**
     * @description - Detail button 클릭시 보여줄 PopupWindow 셋팅
     * @param - v(View) : 보여질 화면
     * @return - None
     * @author - namsh1125
     */
    private fun showPopupwindow(v: View) {

        val popupWindow = PopupWindow(v)
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Set popup window
        popupWindow.contentView = inflater.inflate(R.layout.view_words_i_wrote, null) // 팝업으로 띄울 화면
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) // popup window 크기 설정
        popupWindow.isTouchable = true // popup window 터치 되도록
        popupWindow.isFocusable = true // 포커스

        // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())

        // Show popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0)

        // Set (Popupwindow) add button click listener
        val buttonAdd = popupWindow.contentView.findViewById<ImageButton>(R.id.button_add)
        buttonAdd.setOnClickListener {

            // Run activity
            val intent = Intent(this, AddWordActivity::class.java)
            intent.putExtra("id", diaryId)
            startActivity(intent)

            // Dismiss popup window
            popupWindow.dismiss()
        }

        // (Popupwindow) reyclerview 설정 및 감정 갯수 설정
        val retrofit = RetrofitClient.getInstance()
        val diaryInterestAPI = retrofit.create(DiaryInterestAPI::class.java)

        diaryInterestAPI.getWord(diaryId)
            .enqueue(object : Callback<List<Words>> {

                override fun onResponse(call: Call<List<Words>>, response: Response<List<Words>>) {

                    if (response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val words: ArrayList<Words> = response.body() as ArrayList

                        FlexboxLayoutManager(this@DiaryActivity).apply {
                            flexWrap = FlexWrap.WRAP
                            flexDirection = FlexDirection.ROW
                            justifyContent = JustifyContent.FLEX_START
                        }.let {
                            val recyclerView = popupWindow.contentView.findViewById<RecyclerView>(R.id.recyclerview_words_i_wrote)
                            recyclerView.layoutManager = it
                            recyclerView.adapter = WordsAdapter(words)
                        }

                        // 기쁨 감정 갯수 설정
                        val textviewPleasure = popupWindow.contentView.findViewById<TextView>(R.id.textview_pleasure)
                        textviewPleasure.text = getString(R.string.set_diary_pleasure_count, getWordsCount(words, Sentiment.Pleasure))

                        // 슬픔 감정 갯수 설정
                        val textviewSadness = popupWindow.contentView.findViewById<TextView>(R.id.textview_sadness)
                        textviewSadness.text = getString(R.string.set_diary_sadness_count, getWordsCount(words, Sentiment.Sadness))

                        // 불안 감정 갯수 설정
                        val textviewAnxiety = popupWindow.contentView.findViewById<TextView>(R.id.textview_anxiety)
                        textviewAnxiety.text = getString(R.string.set_diary_anxiety_count, getWordsCount(words, Sentiment.Anxiety))

                        // 상처 감정 갯수 설정
                        val textviewWound = popupWindow.contentView.findViewById<TextView>(R.id.textview_wound)
                        textviewWound.text = getString(R.string.set_diary_wound_count, getWordsCount(words, Sentiment.Wound))

                        // 당황 감정 갯수 설정
                        val textviewEmbarrassment = popupWindow.contentView.findViewById<TextView>(R.id.textview_embarrassment)
                        textviewEmbarrassment.text = getString(R.string.set_diary_embarrassment_count, getWordsCount(words, Sentiment.Embarrassment))

                        // 분노 감정 갯수 설정
                        val textviewAnger = popupWindow.contentView.findViewById<TextView>(R.id.textview_anger)
                        textviewAnger.text = getString(R.string.set_diary_anger_count, getWordsCount(words, Sentiment.Anger))

                    }
                }

                override fun onFailure(call: Call<List<Words>>, t: Throwable) { // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(this@DiaryActivity, "통신 실패!", Toast.LENGTH_SHORT).show()
                }
            })

    }

    /**
     * @description - 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수를 알려주는 함수
     * @param - sentiment(Int) : 감정
     * @return - count(Int) : 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수
     * @author - namsh1125
     */
    private fun getWordsCount(words: List<Words>, sentiment: Sentiment): Int {
        return words.filter { it.sentiment == sentiment.value }.size
    }

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    private fun getTextSentiment(sentiment: Int): String {
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

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    private fun getSentimentColor(sentiment: Int): Int {
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

    enum class Sentiment(val value: Int) {
        Anger(0), Sadness(1), Anxiety(2), Wound(3), Embarrassment(4), Pleasure(5)
    }

}