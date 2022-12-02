package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.ActivityFilteredDetailDiaryBinding

/**
 * @see MainActivity
 * @see DiaryFragment
 * @see gachon.teama.frimo.adapter.FilteredDiaryAdapter
 *
 * 뒤로가기 버튼을 클릭하여 MainActivity로 돌아감
 * RecyclerView에 FilteredDiaryAapter를 사용
 */

class FilteredDetailDiaryActivity :
    BaseActivity<ActivityFilteredDetailDiaryBinding>(ActivityFilteredDetailDiaryBinding::inflate) {

    private lateinit var filteredDiary: ArrayList<Diary>

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setScreen()
        setClickListener()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // When back button clicked
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    /**
     * @description - Set screen
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {

        getDiary()
        setRecyclerview()

        // 어떤 필터 조건이 걸렸는지 설정
        val filter = intent.getStringExtra("filter")
        binding.textviewFilter.text = filter

        // Filtering된 Diary 갯수 설정
        binding.textviewDiaryCount.text = filteredDiary.size.toString() + "개"
    }

    /**
     * @description - Set recyclerview
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() {
        binding.recyclerviewFilteredDiary.setHasFixedSize(true)
        binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(filteredDiary)
    }

    /**
     * @description - 이전 화면에서 전달 받은 data 가져오기
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun getDiary(){
        filteredDiary = intent.getSerializableExtra("filteredDiary") as ArrayList<Diary>
    }

}