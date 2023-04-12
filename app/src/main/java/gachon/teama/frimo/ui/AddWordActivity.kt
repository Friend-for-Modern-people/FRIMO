package gachon.teama.frimo.ui

import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.remote.DiaryServer
import gachon.teama.frimo.databinding.ActivityAddWordBinding
import kotlinx.coroutines.*
import gachon.teama.frimo.data.remote.Diary.SentimentDetail
import gachon.teama.frimo.data.remote.DiaryKeywordsService.AddWordRequest

class AddWordActivity : BaseActivity<ActivityAddWordBinding>(ActivityAddWordBinding::inflate) {

    private val diaryId by lazy { intent.getLongExtra("id", 0) }

    override fun initAfterBinding() {
        setRecyclerview()
        setRadiobutton()
        setClickListener()
    }

    private fun setRecyclerview() {
        lifecycleScope.launch(Dispatchers.Main) {
            val words = DiaryServer.getWord(diaryId)

            FlexboxLayoutManager(this@AddWordActivity).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }.let {
                binding.recyclerviewWords.layoutManager = it
                binding.recyclerviewWords.adapter = WordsAdapter(words)
            }
        }
    }

    // 서로 다른 group의 radio button이 클릭 되었을 때 다른 group button 해제
    private fun setRadiobutton() {
        binding.radiogroup1.setOnCheckedChangeListener { _, _ -> binding.radiogroup2.clearCheck() }
        binding.radiogroup2.setOnCheckedChangeListener { _, _ -> binding.radiogroup1.clearCheck() }
    }

    private fun setClickListener() {
        binding.buttonBack.setOnClickListener { finish() }
        binding.buttonAdd.setOnClickListener { showPopupWindow() }
    }

    // Add button 클릭시 보여줄 PopupWindow
    private fun showPopupWindow() {
        val popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            contentView = LayoutInflater.from(this@AddWordActivity)
                .inflate(R.layout.view_popup_add_word, null, false)
            setBackgroundDrawable(BitmapDrawable())
            isOutsideTouchable = true
            isTouchable = true
            isFocusable = true
        }

        val textView = popupWindow.contentView.findViewById<TextView>(R.id.textview_text)
        textView.text = "\"${binding.edittextAdd.text}\"를(을)\n추가 하시겠어요?"

        popupWindow.contentView.findViewById<TextView>(R.id.textview_text_cancel)
            .setOnClickListener {
                popupWindow.dismiss()
            }

        popupWindow.contentView.findViewById<TextView>(R.id.textview_text_add).setOnClickListener {
            val request = AddWordRequest(
                diaryId,
                binding.edittextAdd.text.toString(),
                getSelectedSentiment(),
                binding.edittextCategory.text.toString()
            )

            lifecycleScope.launch(Dispatchers.Main) {
                if (DiaryServer.addWord(request)) {
                    popupWindow.dismiss()
                    finish()
                    Toast.makeText(this@AddWordActivity, "성공!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddWordActivity, "통신 실패!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
    }

    // 사용자가 추가하고 싶은 단어의 감정을 return하는 함수
    private fun getSelectedSentiment(): Long = with(binding) {
        return when {
            radiobuttonAnger.isChecked -> SentimentDetail.AngerDetail.value
            radiobuttonSadness.isChecked -> SentimentDetail.SadnessDetail.value
            radiobuttonAnxiety.isChecked -> SentimentDetail.AnxietyDetail.value
            radiobuttonWound.isChecked -> SentimentDetail.WoundDetail.value
            radiobuttonEmbarrassment.isChecked -> SentimentDetail.EmbarrassmentDetail.value
            radiobuttonPleasure.isChecked -> SentimentDetail.PleasureDetail.value
            else -> SentimentDetail.Error.value
        }
    }
}