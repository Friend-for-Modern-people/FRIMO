package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.FragmentDiaryFilteredSentimentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class DiaryFilteredBySentimentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredSentimentBinding.inflate(layoutInflater) }

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

        setAnger()
        setSadness()
        setAnxiety()
        setWound()
        setEmbarrassment()
        setPleasure()
    }

    /**
     * @description - 분노로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setAnger() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = anger, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiaryAngerCount.text = "${diary.size}개"

                        // Set layout (anger detail) click listener
                        binding.layoutAngerDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#분노") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set anger diary 1
                        if (diary.size >= 1) {

                            binding.angerDiary1.visibility = View.VISIBLE
                            binding.angerDiary2.visibility = View.INVISIBLE

                            binding.textviewAngerDiary1Date.text = diary[0].createdString
                        }

                        // Set anger diary 1 click listener
                        binding.angerDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set anger diary 2
                        if (diary.size >= 2) {

                            binding.angerDiary1.visibility = View.VISIBLE
                            binding.angerDiary2.visibility = View.VISIBLE

                            binding.textviewAngerDiary2Date.text = diary[1].createdString
                        }

                        // Set anger diary 2 click listener
                        binding.angerDiary2.setOnClickListener {

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
     * @description - 슬픔으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setSadness() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = sadness, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiarySadnessCount.text = "${diary.size}개"

                        // Set layout (sadness detail) click listener
                        binding.layoutSadnessDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#슬픔") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set sadness diary 1
                        if (diary.size >= 1) {

                            binding.sadnessDiary1.visibility = View.VISIBLE
                            binding.sadnessDiary2.visibility = View.INVISIBLE

                            binding.textviewSadnessDiary1Date.text = diary[0].createdString
                        }

                        // Set sadness diary 1 click listener
                        binding.sadnessDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set sadness diary 2
                        if (diary.size >= 2) {

                            binding.sadnessDiary1.visibility = View.VISIBLE
                            binding.sadnessDiary2.visibility = View.VISIBLE

                            binding.textviewSadnessDiary2Date.text = diary[1].createdString
                        }

                        // Set sadness diary 2 click listener
                        binding.sadnessDiary2.setOnClickListener {

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
     * @description - 불안으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setAnxiety() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = anxiety, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiaryAnxietyCount.text = "${diary.size}개"

                        // Set layout (anxiety detail) click listener
                        binding.layoutAnxietyDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#불안") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set anxiety diary 1
                        if (diary.size >= 1) {

                            binding.anxietyDiary1.visibility = View.VISIBLE
                            binding.anxietyDiary2.visibility = View.INVISIBLE

                            binding.textviewAnxietyDiary1Date.text = diary[0].createdString
                        }

                        // Set anxiety diary 1 click listener
                        binding.anxietyDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set anxiety diary 2
                        if (diary.size >= 2) {

                            binding.anxietyDiary1.visibility = View.VISIBLE
                            binding.anxietyDiary2.visibility = View.VISIBLE

                            binding.textviewAnxietyDiary2Date.text = diary[1].createdString
                        }

                        // Set anxiety diary 2 click listener
                        binding.anxietyDiary2.setOnClickListener {

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
     * @description - 상처로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setWound() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = wound, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiaryWoundCount.text = "${diary.size}개"

                        // Set layout (wound detail) click listener
                        binding.layoutWoundDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#상처") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set wound diary 1
                        if (diary.size >= 1) {

                            binding.woundDiary1.visibility = View.VISIBLE
                            binding.woundDiary2.visibility = View.INVISIBLE

                            binding.textviewWoundDiary1Date.text = diary[0].createdString
                        }

                        // Set wound diary 1 click listener
                        binding.woundDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set wound diary 2
                        if (diary.size >= 2) {

                            binding.woundDiary1.visibility = View.VISIBLE
                            binding.woundDiary2.visibility = View.VISIBLE

                            binding.textviewWoundDiary2Date.text = diary[1].createdString
                        }

                        // Set wound diary 2 click listener
                        binding.woundDiary2.setOnClickListener {

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
     * @description - 당황으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setEmbarrassment() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = embarrassment, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiaryEmbarrassmentCount.text = "${diary.size}개"

                        // Set layout (embarrassment detail) click listener
                        binding.layoutEmbarrassmentDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#당황") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set embarrassment diary 1
                        if (diary.size >= 1) {

                            binding.embarrassmentDiary1.visibility = View.VISIBLE
                            binding.embarrassmentDiary2.visibility = View.INVISIBLE

                            binding.textviewEmbarrassmentDiary1Date.text = diary[0].createdString
                        }

                        // Set embarrassment diary 1 click listener
                        binding.embarrassmentDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set embarrassment diary 2
                        if (diary.size >= 2) {

                            binding.embarrassmentDiary1.visibility = View.VISIBLE
                            binding.embarrassmentDiary2.visibility = View.VISIBLE

                            binding.textviewEmbarrassmentDiary2Date.text = diary[1].createdString
                        }

                        // Set embarrassment diary 2 click listener
                        binding.embarrassmentDiary2.setOnClickListener {

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
     * @description - 기쁨으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setPleasure() {

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiaryBySentiment(sentiment = pleasure, userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList

                        // Set layout
                        binding.textviewDiaryPleasureCount.text = "${diary.size}개"

                        // Set layout (pleasure detail) click listener
                        binding.layoutPleasureDetail.setOnClickListener {

                            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                            intent.putExtra("filter", "#기쁨") // 어떤 필터가 걸려있는지 전달
                            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
                            startActivity(intent)
                        }

                        // Set pleasure diary 1
                        if (diary.size >= 1) {

                            binding.pleasureDiary1.visibility = View.VISIBLE
                            binding.pleasureDiary2.visibility = View.INVISIBLE

                            binding.textviewPleasureDiary1Date.text = diary[0].createdString
                        }

                        // Set pleasure diary 1 click listener
                        binding.pleasureDiary1.setOnClickListener {

                            val intent = Intent(requireContext(), DiaryActivity::class.java)
                            intent.putExtra("id", diary[0].id) // Diary id 전달
                            startActivity(intent)
                        }

                        // Set pleasure diary 2
                        if (diary.size >= 2) {

                            binding.pleasureDiary2.visibility = View.VISIBLE

                            binding.textviewPleasureDiary2Date.text = diary[1].createdString
                        }

                        // Set pleasure diary 2 click listener
                        binding.pleasureDiary2.setOnClickListener {

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

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }

}