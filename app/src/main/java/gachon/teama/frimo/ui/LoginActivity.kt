package gachon.teama.frimo.ui

import android.content.Intent
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

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
    private fun setClickListener() {

        // Set login button click listener
        binding.buttonLogin.setOnClickListener{
            // Todo: (Not now) Kakao login 구현 및 관련 내용 저장
            startActivity(Intent(this, AuthorityActivity::class.java))
        }
    }

}