package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityFilteredDetailDiaryBinding

class FilteredDetailDiaryActivity :
    BaseActivity<ActivityFilteredDetailDiaryBinding>(ActivityFilteredDetailDiaryBinding::inflate) {

    override fun initAfterBinding() {

        setClickListener()

    }

    private fun setClickListener() {

        // When back button clicked
        binding.buttonBack.setOnClickListener {
            finish()
        }

    }

}