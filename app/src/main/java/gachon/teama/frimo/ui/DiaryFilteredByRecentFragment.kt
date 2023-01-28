package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Server
import gachon.teama.frimo.databinding.FragmentDiaryFilteredRecentlyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryFilteredByRecentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredRecentlyBinding.inflate(layoutInflater) }

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
    private fun setRecyclerView() = with(binding) {
        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiary(userId)
            recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(diaries)
        }
    }
}