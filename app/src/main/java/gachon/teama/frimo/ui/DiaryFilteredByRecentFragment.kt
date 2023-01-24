package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.FragmentDiaryFilteredRecentlyBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DiaryFilteredByRecentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredRecentlyBinding.inflate(layoutInflater) }

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
        runBlocking {
            setRecyclerView()
        }
        return binding.root
    }

    /**
     * @description - 사용자의 diary를 Recyclerview에 보여줌
     * @see gachon.teama.frimo.adapter.FilteredDiaryAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private suspend fun setRecyclerView() {
        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        lifecycleScope.launch {
            val diaries = withContext(Dispatchers.IO) {
                diaryAPI.getDiary(userId = database.userDao().getUserId())
            }
            binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(diaries)
        }
    }
}