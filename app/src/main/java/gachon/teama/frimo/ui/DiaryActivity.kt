package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.data.remote.Words
import gachon.teama.frimo.databinding.ActivityDiaryBinding

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(ActivityDiaryBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setScreen()
        setClickListener()
    }

    /**
     * @description - 현재 화면에 보여지고 있는 diary의 id 가져오기
     * @param - None
     * @return - id(Int) : diary id
     * @author - namsh1125
     */
    private fun getDiaryId(): Int {
        return intent.getIntExtra("id", 0)
    }

    /**
     * @description - 서버에서 받아온 diary를 화면에 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {

        // Get diary
        val id = getDiaryId()
        val diary = getDiary(id)

        with(binding) {

            textviewDate.text = diary.created
            textviewDiaryTitle.text = diary.title
            textviewDiaryContents.text = diary.content
            textviewSentiment.text = getTextSentiment(diary.sentiment)

            // 감정에 맞게 태그 및 그림 배경 변경
            when (diary.sentiment) {
                pleasure -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.pleasure))
                    imageViewDiary.background.setTint(resources.getColor(R.color.pleasure))
                }
                sadness -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.sadness))
                    imageViewDiary.background.setTint(resources.getColor(R.color.sadness))
                }
                anxiety -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.anxiety))
                    imageViewDiary.background.setTint(resources.getColor(R.color.anxiety))
                }
                wound -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.wound))
                    imageViewDiary.background.setTint(resources.getColor(R.color.wound))
                }
                embarrassment -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.embarrassment))
                    imageViewDiary.background.setTint(resources.getColor(R.color.embarrassment))
                }
                anger -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.anger))
                    imageViewDiary.background.setTint(resources.getColor(R.color.anger))
                }
            }

            // Todo: 키워드 셋팅
            // Todo: (Not now) 댓글 셋팅

        }
    }

    /**
     * @description - 서버로부터 사용자가 작성한 diary를 받아옴
     * @param - id(Int) : 다이어리 id
     * @return - diary(Diary) : 사용자가 작성한 diary
     * @author - namsh1125
     */
    private fun getDiary(id: Int): Diary {

        // Todo: 서버에서 해당 diary 받아오기
        val diary = Diary(
            id = 1,
            title = "1번째 일기",
            content = "나는 오늘 햄버거를 먹었다",
            created = "22.11.24",
            sentiment = pleasure,
        )

        return diary
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

        val id = getDiaryId()
        val words = getWords(id)
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
            intent.putExtra("id", id)
            startActivity(intent)

            // Dismiss popup window
            popupWindow.dismiss()

        }

        // Set (Popupwindow) reyclerview
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            val recyclerView = popupWindow.contentView.findViewById<RecyclerView>(R.id.recyclerview_words_i_wrote)
            recyclerView.layoutManager = it
            recyclerView.adapter = WordsAdapter(words)
        }

        // 기쁨 감정 갯수 설정
        val textviewPleasure= popupWindow.contentView.findViewById<TextView>(R.id.textview_pleasure)
        textviewPleasure.text = "기쁨 ${getWordsCount(pleasure)} "

        // 슬픔 감정 갯수 설정
        val textviewSadness= popupWindow.contentView.findViewById<TextView>(R.id.textview_sadness)
        textviewSadness.text = "슬픔 ${getWordsCount(sadness)} "

        // 불안 감정 갯수 설정
        val textviewAnxiety= popupWindow.contentView.findViewById<TextView>(R.id.textview_anxiety)
        textviewAnxiety.text = "불안 ${getWordsCount(anxiety)} "

        // 상처 감정 갯수 설정
        val textviewWound= popupWindow.contentView.findViewById<TextView>(R.id.textview_wound)
        textviewWound.text = "상처 ${getWordsCount(wound)} "

        // 당황 감정 갯수 설정
        val textviewEmbarrassment= popupWindow.contentView.findViewById<TextView>(R.id.textview_embarrassment)
        textviewEmbarrassment.text = "당황 ${getWordsCount(embarrassment)} "

        // 분노 감정 갯수 설정
        val textviewAnger= popupWindow.contentView.findViewById<TextView>(R.id.textview_anger)
        textviewAnger.text = "분노 ${getWordsCount(anger)} "

    }

    /**
     * @description - 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수를 알려주는 함수
     * @param - sentiment(Int) : 감정
     * @return - count(Int) : 찾고자 하는 감정으로 사용자가 작성한 단어의 갯수
     * @author - namsh1125
     */
    private fun getWordsCount(sentiment: Int): Int {

        val words = getWords(getDiaryId())
        var count = 0

        for(i in 0 until words.size){
            if(words[i].sentiment == sentiment){
                count++
            }
        }

        return count
    }

    /**
     * @description - 서버로부터 사용자가 작성한 단어를 받아옴
     * @param - id(Int) : 다이어리 id
     * @return - wordList(ArrayList<Word>) : 사용자가 작성한 단어들
     * @author - namsh1125
     */
    private fun getWords(id: Int): ArrayList<Words> {

        // Todo: 임시로 작성한 아래 코드 지우고 서버에서 data 받아오기
        val words: MutableList<Words> = mutableListOf()

        words.add(Words("사랑", 5))
        words.add(Words("슬퍼", 1))
        words.add(Words("놀라워", 4))
        words.add(Words("불안", 2))
        words.add(Words("분노", 0))
        words.add(Words("상처", 3))
        words.add(Words("불안", 2))
        words.add(Words("분노", 0))
        words.add(Words("상처", 3))
        words.add(Words("사랑", 5))
        words.add(Words("슬퍼", 1))
        words.add(Words("놀라워", 4))
        words.add(Words("사랑", 5))
        words.add(Words("슬퍼", 1))
        words.add(Words("놀라워", 4))
        words.add(Words("불안", 2))
        words.add(Words("분노", 0))
        words.add(Words("상처", 3))

        return words as ArrayList<Words>
    }

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    private fun getTextSentiment(sentiment: Int): String {
        return when (sentiment) {
            anger -> "# 분노"
            sadness -> "# 슬픔"
            anxiety -> "# 불안"
            wound -> "# 상처"
            embarrassment -> "# 당황"
            else -> "# 기쁨"
        }
    }

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }

}