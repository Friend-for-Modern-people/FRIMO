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

class FilteredDetailDiaryActivity : BaseActivity<ActivityFilteredDetailDiaryBinding>(ActivityFilteredDetailDiaryBinding::inflate) {

    private val diary by lazy { intent.getSerializableExtra("filteredDiary") as List<Diary> }

    override fun initAfterBinding() {
        setRecyclerview()
        setScreen()
        setClickListener()
    }

    // @see gachon.teama.frimo.adapter.FilteredDiaryAdapter
    private fun setRecyclerview() = with(binding) {
        recyclerviewFilteredDiary.setHasFixedSize(true)
        recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(diary)
    }

    private fun setScreen() = with(binding) {
        textviewFilter.text = intent.getStringExtra("filter") // 어떤 필터 조건이 걸렸는지 설정
        textviewDiaryCount.text = "${diary.size}개" // Filtering된 Diary 갯수 설정
    }

    private fun setClickListener() {
        binding.buttonBack.setOnClickListener { finish() }
    }
}