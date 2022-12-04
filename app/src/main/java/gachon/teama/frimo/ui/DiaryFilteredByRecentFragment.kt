package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentDiaryFilteredRecentlyBinding
import kotlin.collections.ArrayList

class DiaryFilteredByRecentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredRecentlyBinding.inflate(layoutInflater) }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setRecyclerView()

        return binding.root
    }

    /**
     * @description - 사용자의 diary를 Recyclerview에 보여줌
     * @see gachon.teama.frimo.adapter.FilteredDiaryAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerView() {
        binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(getDiary())
    }

    /**
     * @description - Server에서 filtering된 diary 가져오기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun getDiary() : ArrayList<Diary> {

        // Todo: 서버에서 filtering된 diary 가져오기
        val diaries: MutableList<Diary> = mutableListOf()

        diaries.add(Diary(id = 5, title = "5번째 일기", content = "나는 오늘 햄버거를 먹었다", created = "22.11.24", sentiment = pleasure))
        diaries.add(Diary(id = 6, title = "6번째 일기", content = "나는 오늘 게임을 했다", created = "22.11.25", sentiment = sadness))
        diaries.add(Diary(id = 7, title = "7번째 일기", content = "나는 집에 가고싶다", created = "22.11.26", sentiment = embarrassment))
        diaries.add(Diary(id = 8, title = "8번째 일기", content = "해외 여행 가고싶다", created = "22.11.27", sentiment = anxiety))

        return diaries as ArrayList<Diary>

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