package gachon.teama.frimo.ui

import gachon.teama.frimo.R
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

class FilteredDetailDiaryActivity : BaseActivity<ActivityFilteredDetailDiaryBinding>(ActivityFilteredDetailDiaryBinding::inflate) {

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
    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { // When back button clicked
            finish()
        }
    }

    /**
     * @description - Set screen
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() = with(binding) {
        setRecyclerview()
        textviewFilter.text = intent.getStringExtra("filter") // 어떤 필터 조건이 걸렸는지 설정
        textviewDiaryCount.text = getString(R.string.set_diary_count, getDiary().size) // Filtering된 Diary 갯수 설정
    }

    /**
     * @description - Set recyclerview
     * @see gachon.teama.frimo.adapter.FilteredDiaryAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setRecyclerview() = with(binding) {
        recyclerviewFilteredDiary.setHasFixedSize(true)
        recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(getDiary())
    }

    /**
     * @description - 이전 화면에서 전달 받은 filtering된 diary 가져오기
     * @param - None
     * @return - diary(ArrayList<Diary>) : 이전 화면에서 전달 받은 filtering된 diary
     * @author - namsh1125
     */
    private fun getDiary(): List<Diary> {
        return intent.getSerializableExtra("filteredDiary") as List<Diary>
    }

}