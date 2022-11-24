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

class FilteredDiaryFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentFilteredDiaryBinding

    // Database
    private lateinit var database: AppDatabase

    // Diary
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var diary1: Diary // filter1 diary1
    private lateinit var diary2: Diary // filter1 diary2
    private lateinit var diary3: Diary // filter2 diary1
    private lateinit var diary4: Diary // filter2 diary2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFilteredDiaryBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireContext())!!

        setDiary()
        setClickListener()

        return binding.root
    }

    private fun setClickListener() {

        binding.layoutFilter1Detail.setOnClickListener {
            startActivity(Intent(requireContext(), FilteredDetailDiaryActivity::class.java))
        }

        binding.layoutFilter2Detail.setOnClickListener {
            startActivity(Intent(requireContext(), FilteredDetailDiaryActivity::class.java))
        }

        // When filter1 diary1 clicked
        binding.filter1Diary1.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary1.id)
            startActivity(intent)

        }

        // When filter1 diary2 clicked
        binding.filter1Diary2.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary2.id)
            startActivity(intent)

        }

        // When filter2 diary1 clicked
        binding.filter2Diary1.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary3.id)
            startActivity(intent)

        }

        // When filter2 diary2 clicked
        binding.filter2Diary2.setOnClickListener {

            // Intent로 diary id 넣어 전달
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary4.id)
            startActivity(intent)

        }

    }

    private fun setDiary() {

        // 전체 diary 셋팅
        getDiaryFromServer()

        // Todo: 필터에 맞게 diary 셋팅
        diary1 = diaryList[0]
        diary2 = diaryList[1]
        diary3 = diaryList[2]
        diary4 = diaryList[3]

        // 화면 setting
        with(binding) {

            // Filter1 Diary1 셋팅
            textviewFilter1Diary1Date.text = diary1.created
            textviewFilter1Diary1Sentiment.text = diary1.sentiment

            // Filter1 Diary2 셋팅
            textviewFilter1Diary2Date.text = diary2.created
            textviewFilter1Diary2Sentiment.text = diary2.sentiment

            // Filter2 Diary1 셋팅
            textviewFilter2Diary1Date.text = diary3.created
            textviewFilter2Diary1Sentiment.text = diary3.sentiment

            // Filter2 Diary2 셋팅
            textviewFilter2Diary2Date.text = diary4.created
            textviewFilter2Diary2Sentiment.text = diary4.sentiment

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


}