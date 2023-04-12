package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.OnboardingFragmentAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    private val onboardingAdapter by lazy { OnboardingFragmentAdapter(this) }

    override fun initAfterBinding() {
        with(binding) {
            viewpager.adapter = onboardingAdapter
            dotsIndicator.attachTo(viewpager)
            buttonNext.setOnClickListener {
                startNextActivity(LoginActivity::class.java)
            }
        }
    }
}
