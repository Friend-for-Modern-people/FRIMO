package gachon.teama.frimo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.databinding.ActivitySetNicknameBinding

class SetNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySetNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // When next button clicked
        binding.buttonNext.setOnClickListener {

            // Todo: nickname 형식에 맞는지 안 맞는지 확인하고 local 저장소에 저장하기
            startActivity(Intent(this, MainActivity::class.java))
        }

    }



}