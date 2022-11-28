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
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentDiaryBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDiaryBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        // Server에서 diary 가져오기
        getDiaryFromServer()

        // Set user nickname
        binding.textviewNickname1.text = database.userDao().getNickname()
        binding.textviewNickname2.text = database.userDao().getNickname()

        // 일기장 개수 설정
        binding.textviewDiaryCount.text = database.diaryDao().getDiaryCount().toString()

        // 최초 실행시 보이는 fragment
        childFragmentManager.beginTransaction().replace(R.id.frame, FilteredDiaryFragment()).commit()

        setClickListener()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setClickListener(){

        binding.buttonFilter.setOnClickListener {

            // FixMe: startActivity로 부르는 방법이 없을까?
            // 클릭시 팝업 윈도우 생성
            val popupWindow = PopupWindow(it)
            val inflater = context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // 팝업으로 띄울 커스텀뷰를 설정하고
            val view: View = inflater.inflate(R.layout.view_popup_sort, null)
            popupWindow.contentView = view

            // popup window 크기 설정
            popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            // popup window 터치 되도록
            popupWindow.isTouchable = true

            // 포커스
            popupWindow.isFocusable = true

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            popupWindow.isOutsideTouchable = true
            popupWindow.setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            popupWindow.showAtLocation(it, Gravity.BOTTOM, 0, 0)

        }

    }

    private fun getDiaryFromServer() {

        // Todo: 아래 코드를 지우고 서버에서 data 가져와 RoomDB에 저장
        //  가능하면 모두 다 지우는게 아니라 없는 것만 저장할 수 있도록
        database.diaryDao().deleteAllDiary()
        database.diaryDao().insert(
            Diary(
                id = 1,
                title = "1번째 일기",
                content = "나는 오늘 햄버거를 먹었다",
                created = "22.11.24",
                sentiment = "# 기쁨",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 2,
                title = "2번째 일기",
                content = "나는 오늘 게임을 했다",
                created = "22.11.25",
                sentiment = "# 슬픔",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 3,
                title = "3번째 일기",
                content = "나는 집에 가고싶다",
                created = "22.11.26",
                sentiment = "# 당황",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 4,
                title = "4번째 일기",
                content = "해외 여행 가고싶다",
                created = "22.11.27",
                sentiment = "# 슬픔",
            )
        )

    }

}