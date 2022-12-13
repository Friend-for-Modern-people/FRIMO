package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import kotlin.collections.ArrayList

class DiaryFilteredByMonthFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentFilteredDiaryBinding.inflate(layoutInflater) }

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

        setCurrentMonth()
        setLastMonth()
    }

    /**
     * @description - 현재 연월에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setCurrentMonth() {

        val year = getCurrentYear()
        val month = getCurrentMonth()

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryByMonth(year = year, month = month, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>>{

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewFilter1.text = "${year}년 ${month}월"
                        binding.textviewFilter1DiaryCount.text = "${diary.size}개"

                        // Set layout (Current month detail) click listener
                        binding.layoutFilter1Detail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "${year}년 ${month}월") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set current month diary 1
                        if (diary.size >= 1) {

                            binding.filter1Diary1.visibility = View.VISIBLE
                            binding.filter1Diary2.visibility = View.INVISIBLE

                            binding.imageViewFilter1Diary1.background.setTint(getColor(diary[0].sentiment))
                            binding.textviewFilter1Diary1Date.text = diary[0].createdString
                            binding.textviewFilter1Diary1Sentiment.text = getTextSentiment(diary[0].sentiment)

                        }

                        // Set current month diary 1 click listener
                        binding.filter1Diary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set current month diary 2
                        if (diary.size >= 2) {

                            binding.filter1Diary2.visibility = View.VISIBLE

                            binding.imageViewFilter1Diary2.background.setTint(getColor(diary[1].sentiment))
                            binding.textviewFilter1Diary2Date.text = diary[1].createdString
                            binding.textviewFilter1Diary2Sentiment.text = getTextSentiment(diary[1].sentiment)

                        }

                        // Set current month diary 2 click listener
                        binding.filter1Diary2.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[1].id) // Diary id 전달
                            startActivity(intent)
                        }

                    } else { // 통신이 실패한 경우(응답코드 3xx, 4xx 등)

                    }
                }

                override fun onFailure(call: Call<List<Diary>>, t: Throwable) { // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(requireContext(), "통신 실패!", Toast.LENGTH_SHORT).show()
                }

            })

    }

    /**
     * @description - 지난달에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setLastMonth() {

        val year = if (getCurrentMonth() - 1 == 0) {
            getCurrentYear() - 1
        } else {
            getCurrentYear()
        }

        val month = if (getCurrentMonth() - 1 == 0) {
            12
        } else {
            getCurrentMonth() - 1
        }

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryByMonth(year = year, month = month, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>>{

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewFilter2.text = "${year}년 ${month}월"
                        binding.textviewFilter2DiaryCount.text = "${diary.size}개"

                        // Set layout (last month detail) click listener
                        binding.layoutFilter2Detail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "${year}년 ${month}월") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set last month diary 1
                        if (diary.size >= 1) {

                            binding.filter2Diary1.visibility = View.VISIBLE
                            binding.filter2Diary2.visibility = View.INVISIBLE

                            binding.imageViewFilter2Diary1.background.setTint(getColor(diary[0].sentiment))
                            binding.textviewFilter2Diary1Date.text = diary[0].createdString
                            binding.textviewFilter2Diary1Sentiment.text = getTextSentiment(diary[0].sentiment)

                        }

                        // Set last month diary 1 click listener
                        binding.filter2Diary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set last month diary 2
                        if (diary.size >= 2) {

                            binding.filter2Diary2.visibility = View.VISIBLE

                            binding.imageViewFilter2Diary2.background.setTint(getColor(diary[1].sentiment))
                            binding.textviewFilter2Diary2Date.text = diary[1].createdString
                            binding.textviewFilter2Diary2Sentiment.text = getTextSentiment(diary[1].sentiment)

                        }

                        // Set last month diary 2 click listener
                        binding.filter2Diary2.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[1].id) // Diary id 전달
                            startActivity(intent)
                        }

                    } else { // 통신이 실패한 경우(응답코드 3xx, 4xx 등)

                    }
                }

                override fun onFailure(call: Call<List<Diary>>, t: Throwable) { // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(requireContext(), "통신 실패!", Toast.LENGTH_SHORT).show()
                }
            })

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
     * @description - 현재 달 받아오기
     * @param - None
     * @return - year(Int) : 현재 연도
     * @author - namsh1125
     */
    private fun getCurrentMonth(): Int {
        return LocalDate.now().monthValue
    }

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    private fun getColor(sentiment: Int): Int {

        return when (sentiment) {
            pleasure -> ContextCompat.getColor(requireContext(), R.color.pleasure)
            sadness -> ContextCompat.getColor(requireContext(), R.color.sadness)
            anxiety -> ContextCompat.getColor(requireContext(), R.color.anxiety)
            wound -> ContextCompat.getColor(requireContext(), R.color.wound)
            embarrassment -> ContextCompat.getColor(requireContext(), R.color.embarrassment)
            anger -> ContextCompat.getColor(requireContext(), R.color.anger)
            else -> ContextCompat.getColor(requireContext(), R.color.black)
        }
    }

    /**
     * @description - Type 변경 ( toString 같은 느낌 )
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - sentiment(String) : String으로 변환된 해당 diary의 대표 감정
     * @author - namsh1125
     */
    private fun getTextSentiment(sentiment: Int): String {
        return when (sentiment) {
            anger -> "#분노"
            sadness -> "#슬픔"
            anxiety -> "#불안"
            wound -> "#상처"
            embarrassment -> "#당황"
            pleasure -> "#기쁨"
            else -> "#에러"
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