package gachon.teama.frimo

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

        binding.buttonKakaoLogin.setOnClickListener{
            // Todo: Kakao login 구현
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buttonGoogleLogin.setOnClickListener{
            // Todo: Google login 구현
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}