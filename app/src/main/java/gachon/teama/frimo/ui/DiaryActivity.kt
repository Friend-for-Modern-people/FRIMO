package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityDiaryBinding

class DiaryActivity : BaseActivity<ActivityDiaryBinding>(ActivityDiaryBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(DiaryActivity())!!

        setDiary()
        setClickListener()

    }

    private fun setDiary() {

        // Get diary from RoomDB
        val id = intent.getIntExtra("id", 0)
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
                startActivity(Intent(this, AddWordActivity::class.java))
                popupWindow.dismiss()
            }

            // Todo: popup window recyclerview 셋팅

        }

    }

}