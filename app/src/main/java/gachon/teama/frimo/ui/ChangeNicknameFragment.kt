package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentChangeNicknameBinding

class ChangeNicknameFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentChangeNicknameBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentChangeNicknameBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        with(binding) {

            buttonBack.setOnClickListener {

                // Fixme: 다른 method 알아볼 것
                fragmentManager?.beginTransaction()?.replace(R.id.frame, SettingFragment())?.commit()
            }

            buttonChange.setOnClickListener {

                val name = edittextNickname.text.toString()

                // 비어있지 않고 기존 닉네임과 다른 경우
                if (name != "" && name != database.userDao().getNickname()){

                    // Todo: 서버에 중복되는 아이디 있는지 확인

                    // 내부 저장소에 업데이트
                    // FixMe: 현재 RoomDB에 접근할 때 MainThread로 접근함. 다른 방법 있는지 알아볼 것
                    database.userDao().updateNickname(name)

                    // Todo: 서버에 유저 정보 반영

                    // Change frame
                    // Fixme: 다른 method 알아볼 것
                    fragmentManager?.beginTransaction()?.replace(R.id.frame, SettingFragment())?.commit()

                } else {
                    Toast.makeText(requireContext(), "You can't change nickname!", Toast.LENGTH_SHORT).show()
                }

            }

        }

        // Inflate the layout for this fragment
        return binding.root
    }


}