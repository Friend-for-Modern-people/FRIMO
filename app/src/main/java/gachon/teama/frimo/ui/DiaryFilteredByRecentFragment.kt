package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.DiaryAPI
import gachon.teama.frimo.data.remote.RetrofitClient
import gachon.teama.frimo.databinding.FragmentDiaryFilteredRecentlyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val retrofit = RetrofitClient.getInstance()
        val diaryAPI = retrofit.create(DiaryAPI::class.java)

        diaryAPI.getDiary(userId = database.userDao().getUserId())
            .enqueue(object : Callback<List<Diary>> {

                override fun onResponse(call: Call<List<Diary>>, response: Response<List<Diary>>) {

                    if(response.isSuccessful) { // 정상적으로 통신이 성공된 경우

                        val diary : ArrayList<Diary> = response.body() as ArrayList
                        binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(diary)

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