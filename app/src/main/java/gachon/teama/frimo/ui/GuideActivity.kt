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
            buttonDetail1.setOnClickListener {

                if (layoutGuide1.isShown) { // If the layout is showing
                    layoutGuide1.visibility = View.GONE
                    buttonDetail1.animate().rotation(0f).setDuration(100).start()
                } else { // If the layout isn't showing
                    layoutGuide1.visibility = View.VISIBLE
                    buttonDetail1.animate().rotation(90f).setDuration(100).start()
                }

            }

            // When the user wants to see the guide for question 2
            buttonDetail2.setOnClickListener {

                if (layoutGuide2.isShown) { // If the layout is showing
                    layoutGuide2.visibility = View.GONE
                    buttonDetail2.animate().rotation(0f).setDuration(100).start()
                } else { // If the layout isn't showing
                    layoutGuide2.visibility = View.VISIBLE
                    buttonDetail2.animate().rotation(90f).setDuration(100).start()
                }

            }

            // When the user wants to see the guide for question 3
            buttonDetail3.setOnClickListener {

                if (layoutGuide3.isShown) { // If the layout is showing
                    layoutGuide3.visibility = View.GONE
                    buttonDetail3.animate().rotation(0f).setDuration(100).start()
                } else { // If the layout isn't showing
                    layoutGuide3.visibility = View.VISIBLE
                    buttonDetail3.animate().rotation(90f).setDuration(100).start()
                }

            }

        }

    }
}