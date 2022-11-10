package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.databinding.ActivitySetNicknameBinding

class SetNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySetNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activate button when available nickname is entered
        with(binding){
            edittextNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(editable: Editable) {
                    buttonNext.isEnabled = editable.isNotEmpty()
                }
            })
        }

        // When next button clicked
        binding.buttonNext.setOnClickListener {

            // Todo: local 저장소에 저장하기
            startActivity(Intent(this, MainActivity::class.java))
        }

    }



}