package gachon.teama.frimo.base

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.ui.DiaryActivity
import gachon.teama.frimo.ui.FilteredDetailDiaryActivity
import java.time.LocalDate

abstract class DiaryFragment : Fragment() {

    /**
     * @description - 현재 연도 받아오기
     * @param - None
     * @return - year(Int) : 현재 연도
     * @author - namsh1125
     */
    fun getCurrentYear(): Int {
        return LocalDate.now().year
    }

    /**
     * @description - 현재 달 받아오기
     * @param - None
     * @return - year(Int) : 현재 달
     * @author - namsh1125
     */
    fun getCurrentMonth(): Int {
        return LocalDate.now().monthValue
    }

    /**
     * @description - 지난 연도 받아오기
     * @param - None
     * @return - year(Int) : 지난 연도
     * @author - namsh1125
     */
    fun getLastYear(): Int {
        return getCurrentYear() - 1
    }

    /**
     * @description - 지난 달의 연도 받아오기
     * @param - None
     * @return - year(Int) : 지난 달의 연도
     * @author - namsh1125
     */
    fun getLastMonthYear(): Int {
        return if (getCurrentMonth() - 1 == 0) {
            getCurrentYear() - 1
        } else {
            getCurrentYear()
        }
    }

    /**
     * @description - 지난 달 받아오기
     * @param - None
     * @return - year(Int) : 지난 달
     * @author - namsh1125
     */
    fun getLastMonth(): Int {
        return if (getCurrentMonth() - 1 == 0) {
            12
        } else {
            getCurrentMonth() - 1
        }
    }

    /**
     * @description - 일기 클릭 리스터
     * @param - id(Long) : 일기 id
     * @author - namsh1125
     */
    inner class DiaryClickListener(id: Long) : View.OnClickListener {
        val diaryId = id

        override fun onClick(v: View?) {
            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diaryId) // Diary id 전달
            startActivity(intent)
        }
    }

    /**
     * @description - 필터링된 일기 자세히 보기 버튼 클릭 리스터
     * @param - filteringString(String) : 어떤 내용으로 필터링 하는지
     * @param - diaries(ArrayList<Diary>) : 필터링된 일기
     * @author - namsh1125
     */
    inner class DetailClickListener(filterString: String, diaries: ArrayList<Diary>) : View.OnClickListener {
        val filter = filterString
        val diaries = diaries

        override fun onClick(v: View?) {
            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.apply {
                this.putExtra("filter", filter) // 어떤 필터가 걸려있는지 전달
                this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
            }
            startActivity(intent)
        }
    }

}