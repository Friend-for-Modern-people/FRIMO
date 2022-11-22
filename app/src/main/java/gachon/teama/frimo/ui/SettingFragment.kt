package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        clickViewEvents()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {

        super.onResume()

        // Set user nickname
        binding.textviewNickname.text = database.userDao().getNickname()
    }

    // 뷰 클릭 이벤트 정의
    private fun clickViewEvents() {

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
            Log.d("logout", "help...me..")
            val dialog = LogoutPopupFragment()
            // 알림창이 띄워져있는 동안 배경 클릭 막기
            dialog.isCancelable = false
            dialog.show(activity?.supportFragmentManager!!, "ConfirmDialog")

//            dialog.show(this.supportFragmentManager, "ConfirmDialog")
        }
    }


}