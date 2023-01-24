package gachon.teama.frimo.ui

import android.view.View
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityNoticeBinding

/***
 * @see MainActivity
 * @see SettingFragment
 */

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setClickListener()
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {

        // Set back button click listener
        buttonBack.setOnClickListener {
            finish()
        }

        // Set layout(notice 1) click listener
        layoutNotice1.setOnClickListener {

            if (layoutNotice1Detail.isShown) { // If the layout is showing
                layoutNotice1Detail.visibility = View.GONE
                buttonDetail1.animate().rotation(0f).setDuration(100).start()
            } else { // If the layout isn't showing
                buttonDetail1.animate().rotation(90f).setDuration(100).start()
                layoutNotice1Detail.visibility = View.VISIBLE

                // Todo: 서버에서 앱 기능 개선 관련 공지 받아오기
            }
        }

        // Set layout(notice 2) click listener
        layoutNotice2.setOnClickListener {

            if (layoutNotice2Detail.isShown) { // If the layout is showing
                layoutNotice2Detail.visibility = View.GONE
                buttonDetail2.animate().rotation(0f).setDuration(100).start()
            } else { // If the layout isn't showing
                layoutNotice2Detail.visibility = View.VISIBLE
                buttonDetail2.animate().rotation(90f).setDuration(100).start()

                // Todo: 서버에서 일기장 생성 관련 이슈 관련 공지 받아오기
            }
        }
    }
}