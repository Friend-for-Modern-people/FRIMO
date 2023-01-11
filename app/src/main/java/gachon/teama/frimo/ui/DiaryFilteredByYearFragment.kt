package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import gachon.teama.frimo.base.DiaryFragment

class DiaryFilteredByYearFragment : DiaryFragment() {

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

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryByYear(year = year, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if (response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary: ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewFilter1.text = getString(R.string.set_diary_year, year)
                        binding.textviewFilter1DiaryCount.text = getString(R.string.set_diary_count, diary.size)

                        // Set layout (current year detail) click listener
                        binding.layoutFilter1Detail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "${year}년") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set current year diary 1
                        if (diary.size >= 1) {

                            binding.filter1Diary1.visibility = View.VISIBLE
                            binding.filter1Diary2.visibility = View.INVISIBLE

                            binding.imageViewFilter1Diary1.background.setTint(
                                ContextCompat.getColor(
                                    requireContext(),
                                    getColor(diary[0].sentiment)
                                )
                            )
                            binding.textviewFilter1Diary1Date.text = diary[0].createdString
                            binding.textviewFilter1Diary1Sentiment.text =
                                getTextSentiment(diary[0].sentiment)

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

                            binding.filter1Diary1.visibility = View.VISIBLE
                            binding.filter1Diary2.visibility = View.VISIBLE

                            binding.imageViewFilter1Diary2.background.setTint(ContextCompat.getColor(requireContext(), getColor(diary[1].sentiment)))
                            binding.textviewFilter1Diary2Date.text = diary[1].createdString
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

                    } else { // 통신이 실패한 경우(응답코드 3xx, 4xx 등)

                    }

                }

                override fun onFailure(call: Call<List<Diary>>, t: Throwable) { // 통신 실패 (인터넷 끊김, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(requireContext(), "통신 실패!", Toast.LENGTH_SHORT).show()
                }

            })

    }

    /**
     * @description - 작년에 작성된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setLastYear() {

        val year = getCurrentYear() - 1

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryByYear(year = year, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if (response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary: ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewFilter2.text = getString(R.string.set_diary_year, year)
                        binding.textviewFilter2DiaryCount.text = getString(R.string.set_diary_count, diary.size)

                        // Set layout (last year detail) click listener
                        binding.layoutFilter2Detail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "${year}년") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set last year diary 1
                        if (diary.size >= 1) {

                            binding.filter2Diary1.visibility = View.VISIBLE
                            binding.filter2Diary2.visibility = View.INVISIBLE

                            binding.imageViewFilter2Diary1.background.setTint(ContextCompat.getColor(requireContext(), getColor(diary[0].sentiment)))
                            binding.textviewFilter2Diary1Date.text = diary[0].createdString
                            binding.textviewFilter2Diary1Sentiment.text = getTextSentiment(diary[0].sentiment)

                        }

                        // Set last year diary 1 click listener
                        binding.filter2Diary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set last year diary 2
                        if (diary.size >= 2) {

                            binding.filter2Diary2.visibility = View.VISIBLE

                            binding.imageViewFilter2Diary2.background.setTint(ContextCompat.getColor(requireContext(), getColor(diary[1].sentiment)))
                            binding.textviewFilter2Diary2Date.text = diary[1].createdString
                            binding.textviewFilter2Diary2Sentiment.text = getTextSentiment(diary[1].sentiment)

                        }

                        // Set last year diary 2 click listener
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

}