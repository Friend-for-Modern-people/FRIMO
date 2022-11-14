package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.data.entities.User
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetNicknameBinding

class SetNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetNicknameBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySetNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this@SetNicknameActivity)!!

        // Activate button when available nickname is entered
        with(binding) {
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

            val name: String = binding.edittextNickname.text.toString()

            // Todo: 서버에 중복되는 아이디 있는지 확인

            // 내부 저장소에 유저 정보 저장하기
            database.userDao().insert(User(nickname = name, recently_talk = 0))

            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}