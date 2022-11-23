package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding

class FilteredDiaryFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentFilteredDiaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFilteredDiaryBinding.inflate(layoutInflater)

        setClickListener()

        return binding.root
    }

    private fun setClickListener() {

        binding.layoutFilter1Detail.setOnClickListener {

        }

        binding.layoutFilter2Detail.setOnClickListener {

        }

        // When filter1 diary1 clicked
        binding.filter1Diary1.setOnClickListener {
            // Todo: Intent로 diary 넣어 전달
            startActivity(Intent(requireContext(), DiaryActivity::class.java))
        }

        // When filter1 diary2 clicked
        binding.filter1Diary2.setOnClickListener {
            // Todo: Intent로 diary 넣어 전달
            startActivity(Intent(requireContext(), DiaryActivity::class.java))
        }

        // When filter2 diary1 clicked
        binding.filter2Diary1.setOnClickListener {
            // Todo: Intent로 diary 넣어 전달
            startActivity(Intent(requireContext(), DiaryActivity::class.java))
        }

        // When filter2 diary2 clicked
        binding.filter2Diary2.setOnClickListener {
            // Todo: Intent로 diary 넣어 전달
            startActivity(Intent(requireContext(), DiaryActivity::class.java))
        }

    }
}