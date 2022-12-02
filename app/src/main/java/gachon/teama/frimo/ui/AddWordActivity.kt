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
import gachon.teama.frimo.data.remote.Words
import gachon.teama.frimo.databinding.ActivityAddWordBinding

class AddWordActivity : BaseActivity<ActivityAddWordBinding>(ActivityAddWordBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setRecyclerview()
        setClickListener()
    }

    /**
     * @description - 사용자가 작성한 단어를 Recyclerview에 보여줌
     * @see gachon.teama.frimo.adapter.WordsAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() {

        // 현재 보여지고 있는 diary가 어떤 것인지 이전 activity에서 받아오기
        val id = intent.getIntExtra("id", 0)

        // Set reyclerview
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recyclerviewWordsIWrote.layoutManager = it
            binding.recyclerviewWordsIWrote.adapter = WordsAdapter(getWords(id))
        }

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

        words.add(Words("사랑", 4))
        words.add(Words("슬퍼", 2))
        words.add(Words("놀라워", 5))
        words.add(Words("불안", 3))
        words.add(Words("분노", 1))
        words.add(Words("사랑", 4))
        words.add(Words("슬퍼", 2))
        words.add(Words("놀라워", 5))
        words.add(Words("불안", 3))
        words.add(Words("분노", 1))
        words.add(Words("사랑", 4))
        words.add(Words("슬퍼", 2))
        words.add(Words("놀라워", 5))
        words.add(Words("불안", 3))
        words.add(Words("분노", 1))

        return words as ArrayList<Words>
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

        // Set add button click listener
        binding.buttonAdd.setOnClickListener {
            showPopupwindow(it)
        }
    }

    /**
     * @description - Add button 클릭시 보여줄 PopupWindow 셋팅
     * @param - v(View) : 보여질 화면
     * @return - None
     * @author - namsh1125
     */
    private fun showPopupwindow(v: View) {

        val popupWindow = PopupWindow(v)
        val inflater = v.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        with(popupWindow) {

            // Set popup window
            contentView = inflater.inflate(R.layout.view_popup_add_word, null) // 팝업으로 띄울 화면
            setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // popup window 크기 설정
            isTouchable = true // popup window 터치 되도록
            isFocusable = true // 포커스

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            isOutsideTouchable = true
            setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            showAtLocation(v, Gravity.CENTER, 0, 0)

            // Set textview
            val text = binding.edittextWordsLikeToAdd.text
            val textView = contentView.findViewById<TextView>(R.id.textview_text)
            textView.text = "\"${text}\"를(을}\n추가 하시겠어요?"

            // Set cancel button click listener
            val buttonCancel = contentView.findViewById<TextView>(R.id.textview_text_cancel)
            buttonCancel.setOnClickListener {
                popupWindow.dismiss()
            }

            // Set logout button click listener
            val buttonLogout = contentView.findViewById<TextView>(R.id.textview_text_add)
            buttonLogout.setOnClickListener {

                popupWindow.dismiss()

                // Todo: 감정 받아오기
                // Todo: 서버에 추가할 단어와 감정, 카테고리를 함께 전송

                Toast.makeText(this@AddWordActivity, "추가되었습니다", Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }
}