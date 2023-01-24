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

class SettingFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentSettingBinding.inflate(layoutInflater) }

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
        setClickListener()
        return binding.root // Inflate the layout for this fragment
    }

    /**
     * @description - 생명주기 onResume
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun onResume() = with(binding) {
        super.onResume()
        textviewNickname.text = database.userDao().getNickname() // Set user nickname
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {

        // Set change nickname button click listener
        buttonChangeNickname.setOnClickListener {
            startActivity(Intent(requireContext(), ChangeNicknameActivity::class.java))
        }

        // Set notice layout click listener
        layoutNotice.setOnClickListener {
            startActivity(Intent(requireContext(), NoticeActivity::class.java))
        }

        // Set guide layout click listener
        layoutGuide.setOnClickListener {
            startActivity(Intent(requireContext(), GuideActivity::class.java))
        }

        // Set logout button click listener
        buttonLogout.setOnClickListener {
            showPopupwindow(it)
        }

    }

    /**
     * @description - Logout button 클릭시 보여줄 PopupWindow 셋팅
     * @param - v(View) : 보여질 화면
     * @return - None
     * @author - namsh1125
     */
    private fun showPopupwindow(v: View) {

        val popupWindow = PopupWindow(v)
        val inflater = context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        with(popupWindow) {

            contentView = inflater.inflate(R.layout.view_popup_logout, null) // 팝업으로 띄울 화면
            setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // popup window 크기 설정
            isTouchable = true // popup window 터치 되도록
            isFocusable = true // 포커스

            // popup window 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
            isOutsideTouchable = true
            setBackgroundDrawable(BitmapDrawable())

            // popup window 보여주기
            showAtLocation(v, Gravity.CENTER, 0, 0)

            // Set cancel button click listener
            contentView.findViewById<TextView>(R.id.textview_text_cancel).setOnClickListener {
                popupWindow.dismiss()
            }

            // Set logout button click listener
            contentView.findViewById<TextView>(R.id.textview_text_logout).setOnClickListener {

                popupWindow.dismiss()
                // Todo: (Not now) 로그아웃 구현
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), SplashActivity::class.java))
            }

        }

    }

}