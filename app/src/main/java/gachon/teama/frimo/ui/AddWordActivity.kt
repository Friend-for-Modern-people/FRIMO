package gachon.teama.frimo.ui

import android.graphics.drawable.BitmapDrawable
import android.util.Log
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
import gachon.teama.frimo.data.entities.Words
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryInterestAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.ActivityAddWordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddWordActivity : BaseActivity<ActivityAddWordBinding>(ActivityAddWordBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setRecyclerview()
        setRadiobutton()
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

        val retrofit = RetrofitClient.getInstance()
        val diaryInterestAPI = retrofit.create(DiaryInterestAPI::class.java)

        diaryInterestAPI.getWord(diaryId = getDiaryId())
            .enqueue(object : Callback<List<Words>> {

                override fun onResponse(call: Call<List<Words>>, response: Response<List<Words>>) {

                    if (response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val words: ArrayList<Words> = response.body() as ArrayList

                        FlexboxLayoutManager(this@AddWordActivity).apply {
                            flexWrap = FlexWrap.WRAP
                            flexDirection = FlexDirection.ROW
                            justifyContent = JustifyContent.FLEX_START
                        }.let {
                            binding.recyclerviewWords.layoutManager = it
                            binding.recyclerviewWords.adapter = WordsAdapter(words)
                        }

                    } else { // 통신이 실패한 경우(응답코드 3xx, 4xx 등)

                    }
                }

                override fun onFailure(call: Call<List<Words>>, t: Throwable) { // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(this@AddWordActivity, "통신 실패!", Toast.LENGTH_SHORT).show()
                }
            })

    }

    /**
     * @description - 현재 보고 있는 창이 어떤 diary를 기반으로 하고 있는지 알려주는 함수
     * @param - None
     * @return - id(Int) : diary id
     * @author - namsh1125
     */
    private fun getDiaryId(): Long {
        return intent.getLongExtra("id", 0)
    }

    /**
     * @description - 서로 다른 group의 radiobutton이 클릭 되었을 때 이전 button 해제
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRadiobutton() {

        with(binding) {

            radiobuttonPleasure.setOnClickListener {
                radiogroup2.clearCheck()
            }
            radiobuttonSadness.setOnClickListener {
                radiogroup2.clearCheck()
            }
            radiobuttonAnxiety.setOnClickListener {
                radiogroup2.clearCheck()
            }
            radiobuttonWound.setOnClickListener {
                radiogroup1.clearCheck()
            }
            radiobuttonEmbarrassment.setOnClickListener {
                radiogroup1.clearCheck()
            }
            radiobuttonAnger.setOnClickListener {
                radiogroup1.clearCheck()
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
            setWindowLayoutMode(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ) // popup window 크기 설정
            isTouchable = true // popup window 터치 되도록
            isFocusable = true // 포커스

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            isOutsideTouchable = true
            setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            showAtLocation(v, Gravity.CENTER, 0, 0)

            // Set textview
            val text = binding.edittextAdd.text
            val textView = contentView.findViewById<TextView>(R.id.textview_text)
            textView.text = "\"${text}\"를(을}\n추가 하시겠어요?"

            // Set cancel button click listener
            val buttonCancel = contentView.findViewById<TextView>(R.id.textview_text_cancel)
            buttonCancel.setOnClickListener {
                popupWindow.dismiss()
            }

            // Set add button click listener
            val buttonAdd = contentView.findViewById<TextView>(R.id.textview_text_add)
            buttonAdd.setOnClickListener {

            }

        }

    }

    /**
     * @description - 사용자가 추가하고 싶은 단어의 감정을 return하는 함수
     * @param - None
     * @return - sentiment(Int) : 사용자가 추가하고 싶은 단어의 감정
     * @author - namsh1125
     */
    private fun getSelectedSentiment(): Int {

        with(binding) {

            if (radiobuttonAnger.isSelected) {
                return anger
            } else if (radiobuttonSadness.isSelected) {
                return sadness
            } else if (radiobuttonAnxiety.isSelected) {
                return anxiety
            } else if (radiobuttonWound.isSelected) {
                return wound
            } else if (radiobuttonEmbarrassment.isSelected) {
                return embarrassment
            } else {
                return pleasure
            }
        }
    }

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
        const val angerDetail = 1
        const val sadnessDetail = 10
        const val anxietyDetail = 19
        const val woundDetail = 28
        const val embarrassmentDetail = 37
        const val pleasureDetail = 46
    }

}