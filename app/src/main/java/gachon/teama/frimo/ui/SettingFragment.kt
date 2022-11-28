package gachon.teama.frimo.ui

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentSettingBinding

/***
 * @see ChangeNicknameActivity
 * @see NoticeActivity
 * @see GuideActivity
 */

class SettingFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentSettingBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        // When change nickname button clicked
        binding.buttonChangeNickname.setOnClickListener {
            startActivity(Intent(requireContext(), ChangeNicknameActivity::class.java))
        }

        // When notice layout clicked
        binding.layoutNotice.setOnClickListener {
            startActivity(Intent(requireContext(), NoticeActivity::class.java))
        }

        // When guide layout clicked
        binding.layoutGuide.setOnClickListener {
            startActivity(Intent(requireContext(), GuideActivity::class.java))
        }

        // When logout button clicked
        binding.buttonLogout.setOnClickListener {
            showLogoutPopup(it)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showLogoutPopup(v:View){

        // 클릭시 팝업 윈도우 생성
        val popupWindow = PopupWindow(v)
        val inflater = context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        with(popupWindow){

            // 팝업으로 띄울 커스텀뷰 설정
            contentView = inflater.inflate(R.layout.view_popup_logout, null)

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

            // When cancel button clicked
            contentView.findViewById<TextView>(R.id.textview_text_cancel).setOnClickListener {
                popupWindow.dismiss()
            }

            // When logout button clicked
            contentView.findViewById<TextView>(R.id.textview_text_logout).setOnClickListener {
                popupWindow.dismiss()
                // Todo: 로그아웃 구현
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), SplashActivity::class.java))
            }

        }

    }

    override fun onResume() {

        super.onResume()

        // Set user nickname
        binding.textviewNickname.text = database.userDao().getNickname()
    }
}