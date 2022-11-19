package gachon.teama.frimo.ui

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.User
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetNicknameBinding

class SetNicknameActivity : BaseActivity<ActivitySetNicknameBinding>(ActivitySetNicknameBinding::inflate) {

    private lateinit var database: AppDatabase

    override fun initAfterBinding() {

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

            // 내부 저장소에 유저 정보 저장하기
            // FixMe: 현재 RoomDB에 접근할 때 MainThread로 접근함. 다른 방법 있는지 알아볼 것
            database.userDao().insert(User(nickname = name, recently_chat_character_id = 0, recently_chat_date = ""))

            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}