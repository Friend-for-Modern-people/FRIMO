package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.OnboardingFragmentAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityOnboardingBinding


class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setScreen()
        setClickListener()
    }

    /**
     * @description - Set viewpager and dots indicator
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() = with(binding) {
        viewpager.adapter = OnboardingFragmentAdapter(this@OnboardingActivity)
        dotsIndicator.attachTo(viewpager)
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {
        buttonNext.setOnClickListener {
            startNextActivity(LoginActivity::class.java)
        }
    }
}