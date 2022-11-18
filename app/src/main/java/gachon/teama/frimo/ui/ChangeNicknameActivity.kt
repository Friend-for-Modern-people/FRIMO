package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity : BaseActivity<ActivityChangeNicknameBinding>(ActivityChangeNicknameBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    override fun initAfterBinding() {

        database = AppDatabase.getInstance(this@ChangeNicknameActivity)!!

        with(binding) {

            // When back button clicked
            buttonBack.setOnClickListener {
                finish()
            }

            // When change nickname button clicked
            buttonChange.setOnClickListener {

                val name = edittextNickname.text.toString()

                // 비어있지 않고 기존 닉네임과 다른 경우
                if (name != "" && name != database.userDao().getNickname()) {

                    // 내부 저장소에 업데이트
                    // FixMe: 현재 RoomDB에 접근할 때 MainThread로 접근함. 다른 방법 있는지 알아볼 것
                    database.userDao().updateNickname(name)

                    finish() // Finish activity

                } else {
                    showToast("You can't change nickname!")
                }

            }

        }

    }

}