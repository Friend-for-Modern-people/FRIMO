package gachon.teama.frimo.ui

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.entities.User
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivitySetNicknameBinding

class SetNicknameActivity : BaseActivity<ActivitySetNicknameBinding>(ActivitySetNicknameBinding::inflate) {

    // Database
    private val database by lazy { AppDatabase.getInstance(this@SetNicknameActivity)!! }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        setEdittext()
        setClickListener()
    }

    /**
     * @description - 본인의 nickname을 입력한 경우에만 버튼이 활성화가 되도록 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setEdittext() {

        // Activate button when available nickname is entered
        binding.edittextNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                binding.buttonNext.isEnabled = editable.isNotEmpty()
            }
        })
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        // Set next button click listener
        binding.buttonNext.setOnClickListener {

            val name: String = binding.edittextNickname.text.toString()

            // 내부 저장소에 유저 정보 저장하기
            // 대화를 한 적이 없는 경우 최근 캐릭터 id는 99로 설정
            database.userDao().insert(User(nickname = name, recently_chat_friend_id = 99, recently_chat_date = ""))

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}