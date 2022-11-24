package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment(){

    // Binding
    private lateinit var binding: FragmentDiaryBinding

    // Database
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDiaryBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        // Set user nickname
        binding.textviewNickname1.text = database.userDao().getNickname()
        binding.textviewNickname2.text = database.userDao().getNickname()

        // 일기장 개수 설정
        binding.textviewDiaryCount.text = database.diaryDao().getDiaryCount().toString()

        // 최초 실행시 보이는 fragment
        childFragmentManager.beginTransaction().replace(R.id.frame, FilteredDiaryFragment()).commit()

        // Inflate the layout for this fragment
        return binding.root
    }

}