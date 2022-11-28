package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityAddWordBinding

class AddWordActivity : BaseActivity<ActivityAddWordBinding>(ActivityAddWordBinding::inflate) {

    override fun initAfterBinding() {
        // Todo: recyclerview 셋팅
        setClickListener()
    }

    private fun setClickListener() {

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonAdd.setOnClickListener {

            var text = binding.edittextWordsLikeToAdd.text.toString()

            // Todo: 어떤 감정이 선택되었는지 받아와서
            // Todo: 서버에 단어와 함께 전송

        }

    }
}