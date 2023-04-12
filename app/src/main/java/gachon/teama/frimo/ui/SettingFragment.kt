package gachon.teama.frimo.ui

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
    private val database by lazy { AppDatabase.getInstance(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        binding.textviewNickname.text = database.userDao().getNickname() // Set user nickname
    }

    private fun setClickListener() {
        with(binding) {
            buttonChangeNickname.setOnClickListener {
                startActivity(Intent(requireContext(), ChangeNicknameActivity::class.java))
            }
            layoutNotice.setOnClickListener {
                startActivity(Intent(requireContext(), NoticeActivity::class.java))
            }
            layoutGuide.setOnClickListener {
                startActivity(Intent(requireContext(), GuideActivity::class.java))
            }
            buttonLogout.setOnClickListener {
                showPopupwindow(it)
            }
        }
    }

    private fun showPopupwindow(v: View) {
        val popupWindow = PopupWindow(v)
        val inflater = LayoutInflater.from(context)

        with(popupWindow) {
            contentView = inflater.inflate(R.layout.view_popup_logout, null)
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isTouchable = true
            isFocusable = true
            isOutsideTouchable = true
            setBackgroundDrawable(BitmapDrawable())

            showAtLocation(v, Gravity.CENTER, 0, 0)

            contentView.findViewById<TextView>(R.id.textview_text_cancel).setOnClickListener {
                dismiss()
            }

            contentView.findViewById<TextView>(R.id.textview_text_logout).setOnClickListener {
                dismiss()
                // Todo: (Not now) 로그아웃 구현
                Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), SplashActivity::class.java))
            }
        }
    }
}