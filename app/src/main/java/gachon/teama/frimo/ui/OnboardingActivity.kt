package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.OnboardingFragmentAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityOnboardingBinding


class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(ActivityOnboardingBinding::inflate) {

    override fun initAfterBinding() {

        // set viewpager and dots indicator
        binding.viewpager.adapter = OnboardingFragmentAdapter(this)
        binding.dotsIndicator.attachTo(binding.viewpager)

        binding.buttonNext.setOnClickListener {
            startNextActivity(LoginActivity::class.java)
        }

    }
}