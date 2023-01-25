package gachon.teama.frimo.base

import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
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

}