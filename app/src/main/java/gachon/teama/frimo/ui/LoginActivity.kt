package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun initAfterBinding() {
        setClickListener()
    }

    private fun setClickListener() {
        binding.buttonLogin.setOnClickListener{
            // Todo: (Not now) Kakao login 구현 및 관련 내용 저장
            startNextActivity(AuthorityActivity::class.java)
        }
    }

}