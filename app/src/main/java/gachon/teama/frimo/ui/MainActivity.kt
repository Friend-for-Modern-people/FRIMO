package gachon.teama.frimo.ui

import android.os.Bundle
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {

        // 최초 실행시 보이는 fragment
        supportFragmentManager.beginTransaction().replace(R.id.frame, ChatFragment()).commit()

        binding.navigationbar.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.chatting -> { // Chatting
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ChatFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.diary -> { // Diary
                    supportFragmentManager.beginTransaction().replace(R.id.frame, DiaryFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                else -> { // Setting
                    supportFragmentManager.beginTransaction().replace(R.id.frame, SettingFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}