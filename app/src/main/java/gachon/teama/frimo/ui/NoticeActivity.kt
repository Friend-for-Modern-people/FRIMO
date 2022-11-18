package gachon.teama.frimo.ui

import android.view.View
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityNoticeBinding

/***
 * @see MainActivity
 * @see SettingFragment
 */

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(ActivityNoticeBinding::inflate) {

    override fun initAfterBinding() {

        with(binding){

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

            // When the user wants to see the guide for question 1
            buttonDetail1.setOnClickListener {

                if (layoutNotice1.isShown) { // If the layout is showing
                    layoutNotice1.visibility = View.GONE
                    buttonDetail1.animate().rotation(0f).setDuration(100).start()
                }
                else { // If the layout isn't showing
                    buttonDetail1.animate().rotation(90f).setDuration(100).start()
                    layoutNotice1.visibility = View.VISIBLE

                    // Todo: 서버에서 앱 기능 개선 관련 공지 받아오기
                }

            }

            // When the user wants to see the guide for question 2
            buttonDetail2.setOnClickListener {

                if (layoutNotice2.isShown) { // If the layout is showing
                    layoutNotice2.visibility = View.GONE
                    buttonDetail2.animate().rotation(0f).setDuration(100).start()
                }
                else { // If the layout isn't showing
                    layoutNotice2.visibility = View.VISIBLE
                    buttonDetail2.animate().rotation(90f).setDuration(100).start()

                    // Todo: 서버에서 일기장 생성 관련 이슈 관련 공지 받아오기
                }

            }

        }

    }
}