package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity : BaseActivity<ActivityChangeNicknameBinding>(ActivityChangeNicknameBinding::inflate) {

    private val database: AppDatabase by lazy { AppDatabase.getInstance(this)!! }

    override fun initAfterBinding() {
        setClickListener()
    }

    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { finish() }

        buttonChange.setOnClickListener {
            val name = edittextNickname.text.toString()
            val beforeNickname = database.userDao().getNickname()
            when {
                name.isBlank() -> showToast("변경할 닉네임을 입력하세요!")
                name == beforeNickname -> showToast("중복되지 않는 닉네임을 입력하세요!")
                else -> updateNickname(name)
            }
        }
    }

    private fun updateNickname(name: String) {
        database.userDao().updateNickname(name)
        showToast("닉네임이 변경되었어요!")
        finish()
    }
}
