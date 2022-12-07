package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import java.time.LocalDate
import kotlin.collections.ArrayList

class DiaryFilteredByYearFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentFilteredDiaryBinding.inflate(layoutInflater) }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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
        val diary = getDiary(year)

        // Set layout
        binding.textviewFilter1.text = "${year}년"
        binding.textviewFilter1DiaryCount.text = "${diary.size}개"

        // Set layout (current year detail) click listener
        binding.layoutFilter1Detail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "${year}년") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if(diary.size == 0) {
            binding.filter1Diary1.visibility = View.GONE
            binding.filter1Diary2.visibility = View.GONE
        }

        // Set current year diary 1
        if (diary.size >= 1) {
            binding.imageViewFilter1Diary1.background.setTint(getColor(diary[0].sentiment))
            binding.textviewFilter1Diary1Date.text = diary[0].created
            binding.textviewFilter1Diary1Sentiment.text = getTextSentiment(diary[0].sentiment)

        } else {
            binding.filter1Diary1.visibility = View.INVISIBLE
        }

        // Set current year diary 1 click listener
        binding.filter1Diary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set current year diary 2
        if (diary.size >= 2) {
            binding.imageViewFilter1Diary2.background.setTint(getColor(diary[1].sentiment))
            binding.textviewFilter1Diary2Date.text = diary[1].created
            binding.textviewFilter1Diary2Sentiment.text = getTextSentiment(diary[1].sentiment)

        } else {
            binding.filter1Diary2.visibility = View.INVISIBLE
        }

        // Set current year diary 2 click listener
        binding.filter1Diary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 작년에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setLastYear() {

        val year = getCurrentYear() - 1
        val diary = getDiary(year)

        // Set layout
        binding.textviewFilter2.text = "${year}년"
        binding.textviewFilter2DiaryCount.text = "${diary.size}개"

        // Set layout (last year detail) click listener
        binding.layoutFilter2Detail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "${year}") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if(diary.size == 0) {
            binding.layoutFilter2.visibility = View.GONE
            binding.filter2Diary1.visibility = View.GONE
            binding.filter2Diary2.visibility = View.GONE
        }

        // Set last year diary 1
        if (diary.size >= 1) {
            binding.imageViewFilter2Diary1.background.setTint(getColor(diary[0].sentiment))
            binding.textviewFilter2Diary1Date.text = diary[0].created
            binding.textviewFilter2Diary1Sentiment.text = getTextSentiment(diary[0].sentiment)

        } else {
            binding.filter2Diary1.visibility = View.INVISIBLE
        }

        // Set last year diary 1 click listener
        binding.filter2Diary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set last year diary 2
        if (diary.size >= 2) {
            binding.imageViewFilter2Diary2.background.setTint(getColor(diary[1].sentiment))
            binding.textviewFilter2Diary2Date.text = diary[1].created
            binding.textviewFilter2Diary2Sentiment.text = getTextSentiment(diary[1].sentiment)

        } else {
            binding.filter2Diary2.visibility = View.INVISIBLE
        }

        // Set last year diary 2 click listener
        binding.filter2Diary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    private fun getColor(sentiment: Int): Int {

        return when (sentiment) {
            pleasure -> resources.getColor(R.color.pleasure)
            sadness -> resources.getColor(R.color.sadness)
            anxiety -> resources.getColor(R.color.anxiety)
            wound -> resources.getColor(R.color.wound)
            embarrassment -> resources.getColor(R.color.embarrassment)
            else -> resources.getColor(R.color.anger)
        }
    }

    /**
     * @description - Server에서 해당 연도로 filtering된 diary 가져오기
     * @param - year(Int) : 필터링하고자 하는 연도
     * @return - diary(ArrayList<Diary>) : 연도로 필터링된 diary
     * @author - namsh1125
     */
    private fun getDiary(year: Int): ArrayList<Diary> {

        val diary: MutableList<Diary> = mutableListOf()

        // Todo: Retrofit을 이용해 연도로 필터링된 diary 가져오기


        return diary as ArrayList
    }

    /**
     * @description - 현재 연도 받아오기
     * @param - None
     * @return - year(Int) : 현재 연도
     * @author - namsh1125
     */
    private fun getCurrentYear(): Int {
        return LocalDate.now().year
    }

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    private fun getTextSentiment(sentiment: Int): String {

        return when (sentiment) {
            anger -> "# 분노"
            sadness -> "# 슬픔"
            anxiety -> "# 불안"
            wound -> "# 상처"
            embarrassment -> "# 당황"
            else -> "# 기쁨"
        }
    }

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }

}