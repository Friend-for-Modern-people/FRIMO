package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        getDiaryFromServer()
        filteringDiary()

        // 필터링된 다이어리를 화면에 setting
        with(binding) {

            // Todo: diary 이미지 셋팅

            // Filter1 Diary1 셋팅
            textviewFilter1Diary1Date.text = filter1Diary[0].created
            textviewFilter1Diary1Sentiment.text = filter1Diary[0].sentiment

            // Filter1 Diary2 셋팅
            textviewFilter1Diary2Date.text = filter1Diary[1].created
            textviewFilter1Diary2Sentiment.text = filter1Diary[1].sentiment

            // Filter2 Diary1 셋팅
            textviewFilter2Diary1Date.text = filter2Diary[0].created
            textviewFilter2Diary1Sentiment.text = filter2Diary[0].sentiment

            // Filter2 Diary2 셋팅
            textviewFilter2Diary2Date.text = filter2Diary[1].created
            textviewFilter2Diary2Sentiment.text = filter2Diary[1].sentiment

        }

    }

    private fun getDiaryFromServer() {

        // Todo: 아래 코드를 지우고 서버에서 data 가져와 RoomDB에 저장
        //  가능하면 모두 다 지우는게 아니라 없는 것만 저장할 수 있도록
        database.diaryDao().deleteAllDiary()
        database.diaryDao().insert(
            Diary(
                id = 1,
                title = "1번째 일기",
                content = "나는 오늘 햄버거를 먹었다",
                created = "22.11.24",
                sentiment = "# 기쁨",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 2,
                title = "2번째 일기",
                content = "나는 오늘 게임을 했다",
                created = "22.11.25",
                sentiment = "# 슬픔",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 3,
                title = "3번째 일기",
                content = "나는 집에 가고싶다",
                created = "22.11.26",
                sentiment = "# 당황",
            )
        )
        database.diaryDao().insert(
            Diary(
                id = 4,
                title = "4번째 일기",
                content = "해외 여행 가고싶다",
                created = "22.11.27",
                sentiment = "# 슬픔",
            )
        )

        // RoomDB에 저장된 모든 diary 가져오기
        diaryList = database.diaryDao().getDiaryList() as ArrayList

    }

    private fun filteringDiary() {

        // Todo: 필터에 맞게 diary 셋팅
        filter1Diary = diaryList
        filter2Diary = diaryList
    }


}