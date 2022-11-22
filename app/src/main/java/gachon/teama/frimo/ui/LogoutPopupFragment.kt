package gachon.teama.frimo.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import gachon.teama.frimo.databinding.ViewPopupLogoutBinding


class LogoutPopupFragment : DialogFragment() {

    // 뷰 바인딩 정의
    private var _binding: ViewPopupLogoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = ViewPopupLogoutBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 취소 버튼 클릭
        binding.textviewTextCancel.setOnClickListener {
            dismiss()
        }

        // 확인 버튼 클릭
        binding.textviewTextLogout.setOnClickListener {
            Toast.makeText(requireContext(), "좀 되어라 진짜", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}