package gachon.teama.frimo.ui

import android.view.View
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityGuideBinding

/***
 * @see MainActivity
 * @see SettingFragment
 */

class GuideActivity : BaseActivity<ActivityGuideBinding>(ActivityGuideBinding::inflate) {

    override fun initAfterBinding() {
        setClickListener()
    }

    private fun setClickListener() {
        setGuideClickListener(binding.layoutGuide1, binding.layoutGuide1Detail, binding.buttonDetail1)
        setGuideClickListener(binding.layoutGuide2, binding.layoutGuide2Detail, binding.buttonDetail2)
        setGuideClickListener(binding.layoutGuide3, binding.layoutGuide3Detail, binding.buttonDetail3)
        binding.buttonBack.setOnClickListener { finish() }
    }

    /**
     * @description - Set guide click listener
     * @param - guideLayout: View, guideDetailLayout: View, guideButton: View
     * @return - None
     */
    private fun setGuideClickListener(guideLayout: View, guideDetailLayout: View, guideButton: View) {
        guideLayout.setOnClickListener {
            if (guideDetailLayout.isShown) {
                guideDetailLayout.visibility = View.GONE
                guideButton.animate().rotation(0f).setDuration(100).start()
            } else {
                guideDetailLayout.visibility = View.VISIBLE
                guideButton.animate().rotation(90f).setDuration(100).start()
            }
        }
    }
}