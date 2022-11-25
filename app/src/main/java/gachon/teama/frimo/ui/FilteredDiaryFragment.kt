package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentFilteredDiaryBinding
import kotlin.collections.ArrayList

/***
 * @see MainActivity
 * @see DiaryFragment
 * @see FilteredDetailDiaryActivity
 * @see DiaryActivity
 * MainActivity - DiaryFragment frame에 부착되어
 * 필터링 된 일기를 더 보기 위해 FilteredDetailDiaryActivity를 호출하고
 * 일기를 자세히 보기 위해 DiaryActivity 호출함
 */
class FilteredDiaryFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentFilteredDiaryBinding

    // Database
    private lateinit var database: AppDatabase

    // Diary
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var filter1Diary: ArrayList<Diary>
    private lateinit var filter2Diary: ArrayList<Diary>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFilteredDiaryBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        setDiary()
        setClickListener()

        return binding.root
    }

    private fun setClickListener() {

        binding.layoutFilter1Detail.setOnClickListener {

            // Intent로 필터링된 diary 넣어 전달
            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "필터1")
            intent.putExtra("filteredDiary", filter1Diary)
            startActivity(intent)
        }

        binding.layoutFilter2Detail.setOnClickListener {

            // Intent로 필터링된 diary 넣어 전달
            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "필터2")
            intent.putExtra("filteredDiary", filter2Diary)
            startActivity(intent)
        }

        // When filter1 diary1 clicked
        binding.filter1Diary1.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", filter1Diary[0].id)
            startActivity(intent)

        }

        // When filter1 diary2 clicked
        binding.filter1Diary2.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", filter1Diary[1].id)
            startActivity(intent)

        }

        // When filter2 diary1 clicked
        binding.filter2Diary1.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", filter2Diary[0].id)
            startActivity(intent)

        }

        // When filter2 diary2 clicked
        binding.filter2Diary2.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", filter2Diary[1].id)
            startActivity(intent)

        }

    }

    private fun setDiary() {

        // diary 필터링
        filteringDiary()

        // 필터링된 다이어리를 화면에 setting
        with(binding) {

            // Filter1 Diary1 셋팅
            textviewFilter1Diary1Date.text = filter1Diary[0].created
            textviewFilter1Diary1Sentiment.text = filter1Diary[0].sentiment
            imageViewFilter1Diary1.background.setTint(getColor(filter1Diary[0].sentiment))

            // Filter1 Diary2 셋팅
            textviewFilter1Diary2Date.text = filter1Diary[1].created
            textviewFilter1Diary2Sentiment.text = filter1Diary[1].sentiment
            imageViewFilter1Diary2.background.setTint(getColor(filter1Diary[1].sentiment))

            // Filter2 Diary1 셋팅
            textviewFilter2Diary1Date.text = filter2Diary[0].created
            textviewFilter2Diary1Sentiment.text = filter2Diary[0].sentiment
            imageViewFilter2Diary1.background.setTint(getColor(filter2Diary[0].sentiment))

            // Filter2 Diary2 셋팅
            textviewFilter2Diary2Date.text = filter2Diary[1].created
            textviewFilter2Diary2Sentiment.text = filter2Diary[1].sentiment
            imageViewFilter2Diary2.background.setTint(getColor(filter1Diary[1].sentiment))

        }

    }

    private fun getColor(sentiment: String): Int{
        when (sentiment) {
            "# 기쁨" -> {
                return resources.getColor(R.color.pleasure)
            }
            "# 슬픔" -> {
                return resources.getColor(R.color.sadness)
            }
            "# 불안" -> {
                return resources.getColor(R.color.anxiety)
            }
            "# 상처" -> {
                return resources.getColor(R.color.wound)
            }
            "# 당황" -> {
                return resources.getColor(R.color.embarrassment)
            }
            else -> {
                return resources.getColor(R.color.anger)
            }
        }
    }

    private fun filteringDiary() {

        // RoomDB에 저장된 모든 diary 가져오기
        diaryList = database.diaryDao().getDiaryList() as ArrayList

        // Todo: 필터에 맞게 diary 셋팅
        filter1Diary = diaryList
        filter2Diary = diaryList
    }

}