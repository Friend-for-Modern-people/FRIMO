package gachon.teama.frimo.ui

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentDiaryBinding

    // Database
    private lateinit var database: AppDatabase

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initVariable()
        setScreen()
        setClickListener()

        return binding.root // Inflate the layout for this fragment
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initVariable() {

        binding = FragmentDiaryBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!
    }

    /**
     * @description - 유저에 맞는 화면 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen(){

        // Set user nickname
        binding.textviewNickname1.text = database.userDao().getNickname()
        binding.textviewNickname2.text = database.userDao().getNickname()

        // 일기장 개수 설정
        binding.textviewDiaryCount.text = getDiaryCount().toString()

        // 최초 실행시 보이는 fragment
        childFragmentManager.beginTransaction().replace(R.id.frame, FilteredDiaryFragment()).commit()

    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener(){

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

        // Todo: 어떤 필터링이 걸렸는지 내부 fragment에 알려주기

    }

    /**
     * @description - Server에 유저가 작성한 diary의 갯수 받아오기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun getDiaryCount(): Int{

        // Todo: 서버에서 저장된 diary의 갯수 가져오기
        return 4
    }

}