package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FilteredDiaryAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.ActivityFilteredDetailDiaryBinding

/***
 * @see MainActivity
 * @see DiaryFragment
 * @see FilteredDiaryFragment
 *
 * 뒤로가기 버튼을 클릭하여 MainActivity로 돌아감
 * RecyclerView에 FilteredDiaryAapter를 사용
 */

class FilteredDetailDiaryActivity :
    BaseActivity<ActivityFilteredDetailDiaryBinding>(ActivityFilteredDetailDiaryBinding::inflate) {

    override fun initAfterBinding() {

        setClickListener()
        setDiary()

    }

    private fun setClickListener() {

        // When back button clicked
        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

    private fun setDiary(){

        // 어떤 필터 조건이 걸렸는지 설정
        val filter = intent.getStringExtra("filter")
        binding.textviewFilter.text = filter

        // 전달 받은 data를 저장해 recyclerview에 설정
        val filteredDiary: ArrayList<Diary> = intent.getSerializableExtra("filteredDiary") as ArrayList<Diary>
        binding.recyclerviewFilteredDiary.setHasFixedSize(true)
        binding.recyclerviewFilteredDiary.adapter = FilteredDiaryAdapter(filteredDiary)

        // Diary 갯수 설정
        binding.textviewDiaryCount.text = filteredDiary.size.toString() + "개"

    }

}