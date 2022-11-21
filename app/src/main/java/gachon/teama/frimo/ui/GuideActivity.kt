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

        with(binding){

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

            // When the user wants to see the guide for question 1
            layoutGuide1.setOnClickListener {

                if (layoutGuide1Detail.isShown) { // If the layout is showing
                    layoutGuide1Detail.visibility = View.GONE
                    buttonDetail1.animate().rotation(0f).setDuration(100).start()
                } else { // If the layout isn't showing
                    layoutGuide1Detail.visibility = View.VISIBLE
                    buttonDetail1.animate().rotation(90f).setDuration(100).start()
                }

            }

            // When the user wants to see the guide for question 2
            layoutGuide2.setOnClickListener {

                if (layoutGuide2Detail.isShown) { // If the layout is showing
                    layoutGuide2Detail.visibility = View.GONE
                    buttonDetail2.animate().rotation(0f).setDuration(100).start()
                } else { // If the layout isn't showing
                    layoutGuide2Detail.visibility = View.VISIBLE
                    buttonDetail2.animate().rotation(90f).setDuration(100).start()
                }

            }

            // When the user wants to see the guide for question 3
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
}