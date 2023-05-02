package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import gachon.teama.frimo.base.DiaryFragment
import gachon.teama.frimo.data.remote.diary.DiaryServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryFilteredByYearFragment : DiaryFragment() {

    // Binding
    private val binding by lazy { FragmentFilteredDiaryBinding.inflate(layoutInflater) }

    // User
    private val userId by lazy { AppDatabase.getInstance(requireContext())!!.userDao().getUserId() }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setScreen()
        return binding.root
    }

    /**
     * @description - Set screen
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {
        setCurrentYear()
        setLastYear()
    }

    /**
     * @description - 현재 연도에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setCurrentYear() {

        val year = getCurrentYear()

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = DiaryServer.getDiaryByYear(userId, year)

            // Set layout
            binding.textviewFilter1.text = getString(R.string.set_diary_year, year)
            binding.textviewFilter1DiaryCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (current year detail) click listener
            binding.layoutFilter1Detail.setOnClickListener(DetailClickListener("${year}년", diaries))

            // Set current year diary 1
            if (diaries.size >= 1) {
                binding.filter1Diary1.visibility = View.VISIBLE
                binding.filter1Diary2.visibility = View.INVISIBLE

                binding.imageViewFilter1Diary1.background.setTint(ContextCompat.getColor(requireContext(), diaries[0].getSentimentColor()))
                binding.textviewFilter1Diary1Date.text = diaries[0].createdString
                binding.textviewFilter1Diary1Sentiment.text = diaries[0].getTextSentiment()

                // Set current year diary 1 click listener
                binding.filter1Diary1.setOnClickListener(DiaryClickListener(diaries[0].id))
            }

            // Set current year diary 2
            if (diaries.size >= 2) {
                binding.filter1Diary2.visibility = View.VISIBLE

                binding.imageViewFilter1Diary2.background.setTint(ContextCompat.getColor(requireContext(), diaries[1].getSentimentColor()))
                binding.textviewFilter1Diary2Date.text = diaries[1].createdString
                binding.textviewFilter1Diary2Sentiment.text = diaries[1].getTextSentiment()

                // Set current year diary 2 click listener
                binding.filter1Diary2.setOnClickListener(DiaryClickListener(diaries[1].id))
            }
        }
    }

    /**
     * @description - 작년에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setLastYear() {

        val year = getLastYear()

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = DiaryServer.getDiaryByYear(userId, year)

            // Set layout
            binding.textviewFilter2.text = getString(R.string.set_diary_year, year)
            binding.textviewFilter2DiaryCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (last year detail) click listener
            binding.layoutFilter2Detail.setOnClickListener(DetailClickListener("${year}년", diaries))

            // Set last year diary 1
            if (diaries.size >= 1) {
                binding.filter2Diary1.visibility = View.VISIBLE
                binding.filter2Diary2.visibility = View.INVISIBLE

                binding.imageViewFilter2Diary1.background.setTint(ContextCompat.getColor(requireContext(), diaries[0].getSentimentColor()))
                binding.textviewFilter2Diary1Date.text = diaries[0].createdString
                binding.textviewFilter2Diary1Sentiment.text = diaries[0].getTextSentiment()

                // Set last year diary 1 click listener
                binding.filter2Diary1.setOnClickListener(DiaryClickListener(diaries[0].id))
            }

            // Set last year diary 2
            if (diaries.size >= 2) {
                binding.filter2Diary2.visibility = View.VISIBLE

                binding.imageViewFilter2Diary2.background.setTint(ContextCompat.getColor(requireContext(), diaries[1].getSentimentColor()))
                binding.textviewFilter2Diary2Date.text = diaries[1].createdString
                binding.textviewFilter2Diary2Sentiment.text = diaries[1].getTextSentiment()

                // Set last year diary 2 click listener
                binding.filter2Diary2.setOnClickListener(DiaryClickListener(diaries[1].id))
            }
        }
    }

}