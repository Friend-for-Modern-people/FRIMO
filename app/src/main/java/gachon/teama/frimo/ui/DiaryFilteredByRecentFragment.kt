package gachon.teama.frimo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.diary.DiaryServer
import gachon.teama.frimo.databinding.FragmentDiaryFilteredRecentlyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DiaryFilteredByRecentFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentDiaryFilteredRecentlyBinding

    // User
    private val userId by lazy { AppDatabase.getInstance(requireContext())!!.userDao().getUserId() }

    // CoroutineScope
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDiaryFilteredRecentlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        scope.launch {
            val diaries = DiaryServer.getDiary(userId)
            binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(diaries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
    }
}