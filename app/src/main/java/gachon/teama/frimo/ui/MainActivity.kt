package gachon.teama.frimo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.R
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        // 최초 실행시 보이는 fragment
        fragmentManager.beginTransaction().replace(R.id.frame, ChatFragment()).commit()

        binding.navigationbar.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when (it.itemId) {
                R.id.chatting -> { // Chatting
                    fragmentManager.beginTransaction().replace(R.id.frame, ChatFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.diary -> { // Diary
                    fragmentManager.beginTransaction().replace(R.id.frame, DiaryFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                else -> { // Setting
                    fragmentManager.beginTransaction().replace(R.id.frame, SettingFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}