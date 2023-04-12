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
        setClickListener()
    }

    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { finish() }

        layoutNotice1.setOnClickListener {
            if (layoutNotice1Detail.isShown) {
                layoutNotice1Detail.visibility = View.GONE
                setButtonRotation(buttonDetail1, 0f)
            } else {
                setButtonRotation(buttonDetail1, 90f)
                layoutNotice1Detail.visibility = View.VISIBLE

                // Todo: 서버에서 앱 기능 개선 관련 공지 받아오기
            }
        }

        layoutNotice2.setOnClickListener {
            if (layoutNotice2Detail.isShown) {
                layoutNotice2Detail.visibility = View.GONE
                setButtonRotation(buttonDetail2, 0f)
            } else {
                layoutNotice2Detail.visibility = View.VISIBLE
                setButtonRotation(buttonDetail2, 90f)

                // Todo: 서버에서 일기장 생성 관련 이슈 관련 공지 받아오기
            }
        }
    }

    private fun setButtonRotation(view: View, rotation: Float) {
        view.animate().rotation(rotation).setDuration(100).start()
    }
}