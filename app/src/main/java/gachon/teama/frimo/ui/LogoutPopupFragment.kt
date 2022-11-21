package gachon.teama.frimo.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import gachon.teama.frimo.databinding.ViewPopupLogoutBinding

class LogoutPopupFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false // false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
    }

    private lateinit var binding: ViewPopupLogoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewPopupLogoutBinding.inflate(inflater, container, false)

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewTextCancel.setOnClickListener {
            dismiss()
        }

        binding.textviewTextLogout.setOnClickListener {

            // Todo: 로그아웃 구현
            Toast.makeText(requireContext(), "logout button clicked", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}