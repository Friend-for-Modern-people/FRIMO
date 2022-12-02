package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Words
import gachon.teama.frimo.databinding.ActivityDiaryBinding

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(ActivityDiaryBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    // Diary id
    private var id : Int = 0

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(DiaryActivity())!!

        setDiary()
        setClickListener()

    }

    private fun setDiary() {

        // Get diary from RoomDB
        id = intent.getIntExtra("id", 0)
        val diary = database.diaryDao().getDiary(id)

        // Set diary
        with(binding) {
            textviewDate.text = diary.created
            textviewDiaryTitle.text = diary.title
            textviewDiaryContents.text = diary.content
            textviewSentiment.text = diary.sentiment

            // 감정 태그 색상 지정
            when (diary.sentiment) {
                "# 기쁨" -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.pleasure))
                }
                "# 슬픔" -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.sadness))
                }
                "# 불안" -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.anxiety))
                }
                "# 상처" -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.wound))
                }
                "# 당황" -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.embarrassment))
                }
                else -> {
                    textviewSentiment.background.setTint(resources.getColor(R.color.anger))
                }
            }

            // 기존 이미지에서 배경색 변경
            when (diary.sentiment) {
                "# 기쁨" -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.pleasure))
                }
                "# 슬픔" -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.sadness))
                }
                "# 불안" -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.anxiety))
                }
                "# 상처" -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.wound))
                }
                "# 당황" -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.embarrassment))
                }
                else -> {
                    imageViewDiary.background.setTint(resources.getColor(R.color.anger))
                }
            }
        }
    }

    private fun setClickListener() {

        // When back button clicked
        binding.buttonBack.setOnClickListener {
            finish()
        }

        // When share button clicked
        binding.buttonShare.setOnClickListener {
            // Todo: 일기 공유하기
            showToast("추후 업데이트 예정입니다 :)")
        }

        // When detail button clicked
        binding.buttonDetail.setOnClickListener {

            var words = database.wordsDao().getWords(id) as ArrayList

            if(words == null){ // 만약 저장된 단어들이 없다면
                words = getWordsIWroteFromServer()
            }

            // FixMe: startActivity로 부르는 방법이 없을까?
            // 클릭시 팝업 윈도우 생성
            val popupWindow = PopupWindow(it)
            val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // 팝업으로 띄울 커스텀뷰를 설정하고
            val view: View = inflater.inflate(R.layout.view_words_i_wrote, null)
            popupWindow.contentView = view

            // popup window 크기 설정
            popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            // popup window 터치 되도록
            popupWindow.isTouchable = true

            // 포커스
            popupWindow.isFocusable = true

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            popupWindow.isOutsideTouchable = true
            popupWindow.setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)

            // (popup window) when add button clicked
            popupWindow.contentView.findViewById<ImageButton>(R.id.button_add).setOnClickListener {

                // Todo: Intent로 Diary id 넘기기
                startActivity(Intent(this, AddWordActivity::class.java))
                popupWindow.dismiss()
            }

            // Set popup window reyclerview
            FlexboxLayoutManager(this).apply{
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }.let {
                var recyclerView = popupWindow.contentView.findViewById<RecyclerView>(R.id.recyclerview_words_i_wrote)
                recyclerView.layoutManager = it
                recyclerView.adapter = WordsAdapter(words)
            }

        }

        // Todo: popup window 감정 갯수 보여주기

    }

    private fun getWordsIWroteFromServer() : ArrayList<Words>{

        // Todo: 아래 코드를 지우고 서버에서 내가 작성한 단어 받아오기
        // Todo: RoomDB에 저장하기
        database.wordsDao().insert(Words(0,1, "사랑", 6))
        database.wordsDao().insert(Words(1,1, "슬퍼", 2))
        database.wordsDao().insert(Words(2,1, "놀라워", 5))
        database.wordsDao().insert(Words(3,1, "불안", 3))
        database.wordsDao().insert(Words(4,1, "분노", 1))
        database.wordsDao().insert(Words(5,1, "사랑", 6))
        database.wordsDao().insert(Words(6,1, "슬퍼", 2))
        database.wordsDao().insert(Words(7,1, "놀라워", 5))
        database.wordsDao().insert(Words(8,1, "불안", 3))
        database.wordsDao().insert(Words(9,1, "분노", 1))
        database.wordsDao().insert(Words(10,1, "사랑", 6))
        database.wordsDao().insert(Words(11,1, "슬퍼", 2))
        database.wordsDao().insert(Words(12,1, "놀라워", 5))
        database.wordsDao().insert(Words(13,1, "불안", 3))
        database.wordsDao().insert(Words(14,1, "분노", 1))

        return database.wordsDao().getWords(id) as ArrayList

    }

}