package gachon.teama.frimo.ui

import android.view.View
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityGuideBinding

/***
 * @see MainActivity
 * @see SettingFragment
 */

class GuideActivity : BaseActivity<ActivityGuideBinding>(ActivityGuideBinding::inflate) {

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

        // Set guide1 layout click listener
        layoutGuide1.setOnClickListener {

            if (layoutGuide1Detail.isShown) { // If the layout is showing
                layoutGuide1Detail.visibility = View.GONE
                buttonDetail1.animate().rotation(0f).setDuration(100).start()
            } else { // If the layout isn't showing
                layoutGuide1Detail.visibility = View.VISIBLE
                buttonDetail1.animate().rotation(90f).setDuration(100).start()
            }
        }

        // Set guide2 layout click listener
        layoutGuide2.setOnClickListener {

            if (layoutGuide2Detail.isShown) { // If the layout is showing
                layoutGuide2Detail.visibility = View.GONE
                buttonDetail2.animate().rotation(0f).setDuration(100).start()
            } else { // If the layout isn't showing
                layoutGuide2Detail.visibility = View.VISIBLE
                buttonDetail2.animate().rotation(90f).setDuration(100).start()
            }
        }

        // Set guide3 layout click listener
        layoutGuide3.setOnClickListener {

            if (layoutGuide3Detail.isShown) { // If the layout is showing
                layoutGuide3Detail.visibility = View.GONE
                buttonDetail3.animate().rotation(0f).setDuration(100).start()
            } else { // If the layout isn't showing
                layoutGuide3Detail.visibility = View.VISIBLE
                buttonDetail3.animate().rotation(90f).setDuration(100).start()
            }
        }
    }
}