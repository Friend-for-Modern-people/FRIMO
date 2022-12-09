package gachon.teama.frimo.ui

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentDiaryBinding
import gachon.teama.frimo.retrofit.DiaryAPI
import gachon.teama.frimo.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiaryFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryBinding.inflate(layoutInflater) }

    // Database
    private val database by lazy { AppDatabase.getInstance(requireContext())!! }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setScreen()
        setClickListener()

        return binding.root // Inflate the layout for this fragment
    }

    /**
     * @description - 유저에 맞는 화면 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {

        // Set user nickname
        binding.textviewNickname1.text = database.userDao().getNickname()
        binding.textviewNickname2.text = database.userDao().getNickname()

        // 일기장 개수 설정
        setDiaryCount()

        // 최초 실행시 보이는 fragment 셋팅
        childFragmentManager.beginTransaction().replace(R.id.frame, DiaryFilteredByYearFragment()).commit()

    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // Set filter button click listener
        binding.buttonFilter.setOnClickListener {
            showPopupwindow(it)
        }
    }

    /**
     * @description - Filter button 클릭시 보여줄 PopupWindow 셋팅
     * @param - v(View) : 보여질 화면
     * @return - None
     * @author - namsh1125
     */
    private fun showPopupwindow(v: View) {

        // 클릭시 팝업 윈도우 생성
        val popupWindow = PopupWindow(v)
        val inflater = context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Set popup window
        popupWindow.contentView = inflater.inflate(R.layout.view_popup_sort, null) // 팝업으로 띄울 화면
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // popup window 크기 설정
        popupWindow.isTouchable = true // popup window 터치 되도록
        popupWindow.isFocusable = true // 포커스

        // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())

        // popup window 보여주기
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0)

        // Set radiobutton click listener
        val radiogroup1 = popupWindow.contentView.findViewById<RadioGroup>(R.id.radiogroup1)
        val radiogroup2 = popupWindow.contentView.findViewById<RadioGroup>(R.id.radiogroup2)
        val filterYear = popupWindow.contentView.findViewById<RadioButton>(R.id.radiobutton_year)
        val filterMonth = popupWindow.contentView.findViewById<RadioButton>(R.id.radiobutton_month)
        val filterSentiment = popupWindow.contentView.findViewById<RadioButton>(R.id.radiobutton_sentiment)
        val filterRecent = popupWindow.contentView.findViewById<RadioButton>(R.id.radiobutton_recent)

        filterYear.setOnClickListener {

            radiogroup2.clearCheck() // 하위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(resources.getColor(R.color.skin))
            filterMonth.setTextColor(resources.getColor(R.color.gray6))
            filterSentiment.setTextColor(resources.getColor(R.color.gray6))
            filterRecent.setTextColor(resources.getColor(R.color.gray6))
        }

        filterMonth.setOnClickListener {

            radiogroup2.clearCheck() // 하위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(resources.getColor(R.color.gray6))
            filterMonth.setTextColor(resources.getColor(R.color.skin))
            filterSentiment.setTextColor(resources.getColor(R.color.gray6))
            filterRecent.setTextColor(resources.getColor(R.color.gray6))
        }

        filterSentiment.setOnClickListener {

            radiogroup1.clearCheck() // 상위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(resources.getColor(R.color.gray6))
            filterMonth.setTextColor(resources.getColor(R.color.gray6))
            filterSentiment.setTextColor(resources.getColor(R.color.skin))
            filterRecent.setTextColor(resources.getColor(R.color.gray6))
        }

        filterRecent.setOnClickListener {

            radiogroup1.clearCheck() // 상위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(resources.getColor(R.color.gray6))
            filterMonth.setTextColor(resources.getColor(R.color.gray6))
            filterSentiment.setTextColor(resources.getColor(R.color.gray6))
            filterRecent.setTextColor(resources.getColor(R.color.skin))
        }

        // Set apply button click listener
        val buttonApply = popupWindow.contentView.findViewById<Button>(R.id.button_apply)
        buttonApply.setOnClickListener {

            if (filterYear.isChecked) {
                childFragmentManager.beginTransaction().replace(R.id.frame, DiaryFilteredByYearFragment()).commit()
            } else if (filterMonth.isChecked) {
                childFragmentManager.beginTransaction().replace(R.id.frame, DiaryFilteredByMonthFragment()).commit()
            } else if (filterSentiment.isChecked) {
                childFragmentManager.beginTransaction().replace(R.id.frame, DiaryFilteredBySentimentFragment()).commit()
            } else {
                childFragmentManager.beginTransaction().replace(R.id.frame, DiaryFilteredByRecentFragment()).commit()
            }

            popupWindow.dismiss()

        }

    }

    /**
     * @description - Server에 유저가 작성한 diary의 갯수 설정하기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setDiaryCount() {

        var diaryCount : Int? = null

        val userID = database.userDao().getUserId()
        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryCount(userID)
            .enqueue(object : Callback<Int> {

                override fun onResponse(call: Call<Int>, response: Response<Int>) {

                    if(response.isSuccessful) {

                        // 정상적으로 통신이 성공된 경우
                        diaryCount = response.body()!!
                        binding.textviewDiaryCount.text = diaryCount.toString()
                    } else {

                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        diaryCount = 0
                        binding.textviewDiaryCount.text = diaryCount.toString()
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {

                    // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(requireContext(), "통신 실패!", Toast.LENGTH_SHORT).show()
                    binding.textviewDiaryCount.text = diaryCount.toString()
                }

            })

    }

}