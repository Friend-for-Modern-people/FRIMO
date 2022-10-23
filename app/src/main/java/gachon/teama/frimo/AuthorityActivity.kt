package gachon.teama.frimo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import gachon.teama.frimo.databinding.ActivityAuthorityBinding

class AuthorityActivity : ComponentActivity() {

    private lateinit var binding: ActivityAuthorityBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityAuthorityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            startActivity(Intent(this, SetNicknameActivity::class.java))
        }

    }
}