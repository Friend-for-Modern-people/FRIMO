package gachon.teama.frimo.ui

import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityChangeNicknameBinding

class ChangeNicknameActivity :
    BaseActivity<ActivityChangeNicknameBinding>(ActivityChangeNicknameBinding::inflate) {

    // Database
    private lateinit var database: AppDatabase

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {

        initVariable()
        setClickListener()
    }

    /**
     * @description - 변수 셋팅
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initVariable() {
        database = AppDatabase.getInstance(this@ChangeNicknameActivity)!!
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() {

        with(binding) {

            // Set back button click listener
            buttonBack.setOnClickListener {
                finish()
            }

            // Set change nickname button click listener
            buttonChange.setOnClickListener {

                val name = edittextNickname.text.toString()

                if (name == "") {
                    showToast("변경할 닉네임을 입력하세요!")
                } else if (name == database.userDao().getNickname()) {
                    showToast("중복되지 않는 닉네임을 입력하세요!")
                } else {
                    database.userDao().updateNickname(name) // 내부 저장소에 변경된 닉네임 업데이트
                    showToast("닉네임이 변경되었어요!")
                    finish() // Finish activity
                }
            }

        }
    }

}