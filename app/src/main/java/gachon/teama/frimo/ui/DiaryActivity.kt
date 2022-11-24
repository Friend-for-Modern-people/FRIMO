package gachon.teama.frimo.ui

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

            // Todo: image가 있는 경우 image 셋팅
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

    }

}