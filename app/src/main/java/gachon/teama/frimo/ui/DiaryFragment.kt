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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentDiaryBinding
import gachon.teama.frimo.data.remote.Server
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        setNickname()
        setDiaryCount()
        changeFragment(R.id.frame, DiaryFilteredByYearFragment()) // 최초 실행시 보이는 fragment 셋팅
    }

    /**
     * @description - 화면에 user nickname 설정하기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setNickname() = with(binding) {
        textviewNickname1.text = database.userDao().getNickname()
        textviewNickname2.text = database.userDao().getNickname()
    }

    /**
     * @description - 화면에 유저가 작성한 diary의 갯수 설정하기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setDiaryCount() = with(binding) {
        CoroutineScope(Dispatchers.Main).launch {
            val count = Server.getDiaryCount(userId = database.userDao().getUserId())
            textviewDiaryCount.text = count.toString()
        }
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
            filterYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
            filterMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterSentiment.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
        }

        filterMonth.setOnClickListener {
            radiogroup2.clearCheck() // 하위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
            filterSentiment.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
        }

        filterSentiment.setOnClickListener {
            radiogroup1.clearCheck() // 상위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterSentiment.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
            filterRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
        }

        filterRecent.setOnClickListener {
            radiogroup1.clearCheck() // 상위 라디오 버튼 선택 해제

            // text 색상 변경
            filterYear.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterMonth.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterSentiment.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray6))
            filterRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.skin))
        }

        // Set apply button click listener
        val buttonApply = popupWindow.contentView.findViewById<Button>(R.id.button_apply)
        buttonApply.setOnClickListener {
            when {
                filterYear.isChecked -> changeFragment(R.id.frame, DiaryFilteredByYearFragment())
                filterMonth.isChecked -> changeFragment(R.id.frame, DiaryFilteredByMonthFragment())
                filterSentiment.isChecked -> changeFragment(R.id.frame, DiaryFilteredBySentimentFragment())
                filterRecent.isChecked -> changeFragment(R.id.frame, DiaryFilteredByRecentFragment())
            }

            popupWindow.dismiss()
        }
    }

    /**
     * @description - Fragment 전환
     * @param - containerViewId(Int) : 띄워질 fragment의 id
     * @param - fragment : 띄워질 fragment
     * @return - None
     * @author - namsh1125
     */
    private fun changeFragment(containerViewId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
    }
}