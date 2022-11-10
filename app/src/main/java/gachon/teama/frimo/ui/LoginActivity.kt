package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener{
            // Todo: Kakao login 구현
            //  내부 저장소에 관련 정보 저장
            startActivity(Intent(this, AuthorityActivity::class.java))
        }

    }
}