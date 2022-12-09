package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.entities.Diary
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
     * @description - Server에서 최신순으로 필터링된 diary 가져오기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun getDiary() : ArrayList<Diary> {

        val diaries: MutableList<Diary> = mutableListOf()

        // Todo: Retrofit을 이용해 최신순으로 필터링된 diary 가져오기

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