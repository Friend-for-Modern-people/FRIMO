package gachon.teama.frimo.ui

import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import gachon.teama.frimo.R
import gachon.teama.frimo.adapter.WordsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityAddWordBinding

class AddWordActivity : BaseActivity<ActivityAddWordBinding>(ActivityAddWordBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(DiaryActivity())!!

        setRecyclerview()
        setClickListener()
    }

    private fun setRecyclerview() {

        // 현재 보여지고 있는 diary가 어떤 것인지 받아오고
        var id = intent.getIntExtra("id", 0)

        // RoomDB에서 해당 diary에 작성된 단어 가져오기
        val words = database.wordsDao().getWords(id)

        // Set reyclerview
        FlexboxLayoutManager(this).apply{
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recyclerviewWordsIWrote.layoutManager = it
            binding.recyclerviewWordsIWrote.adapter = WordsAdapter(words as ArrayList)
        }

    }

    private fun setClickListener() {

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonAdd.setOnClickListener {

            var text = binding.edittextWordsLikeToAdd.text.toString()
            // Todo: 감정 받아오기
            showAddPopup(it)
        }

    }

    private fun showAddPopup(v: View){

        // 클릭시 팝업 윈도우 생성
        val popupWindow = PopupWindow(v)
        val inflater = v.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        with(popupWindow){

            // 팝업으로 띄울 커스텀뷰 설정
            contentView = inflater.inflate(R.layout.view_popup_add_word, null)

            // popup window 크기 설정
            setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            // popup window 터치 되도록
            isTouchable = true

            // 포커스
            isFocusable = true

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            isOutsideTouchable = true
            setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            showAtLocation(v, Gravity.CENTER, 0, 0)

            // 추가할 단어 배치
            val text = binding.edittextWordsLikeToAdd.text
            contentView.findViewById<TextView>(R.id.textview_text).text = "\"${text}\"를(을}\n추가 하시겠어요?"

            // When cancel button clicked
            contentView.findViewById<TextView>(R.id.textview_text_cancel).setOnClickListener {
                popupWindow.dismiss()
            }

            // When logout button clicked
            contentView.findViewById<TextView>(R.id.textview_text_add).setOnClickListener {
                popupWindow.dismiss()
                // Todo: RoomDB에 해당 내용 update
                // Todo: 서버에 단어와 선택된 감정을 함께 전송
                Toast.makeText(this@AddWordActivity, "추가되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }
}