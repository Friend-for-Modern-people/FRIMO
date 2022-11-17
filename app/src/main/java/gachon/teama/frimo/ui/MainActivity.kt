package gachon.teama.frimo.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gachon.teama.frimo.R
import gachon.teama.frimo.databinding.ActivityLoginBinding
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private lateinit var binding : ActivityMainBinding

    private var chattingFragment: ChattingFragment? = null
    private var diaryFragment: DiaryFragment? = null
    private var settingFragment: SettingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation(){

        // 최초로 보이는 프래그먼트
        chattingFragment = ChattingFragment()
        fragmentManager.beginTransaction().replace(R.id.frame,chattingFragment!!).commit()

        binding.navigationbar.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when(it.itemId){
                R.id.chatting ->{
                    if(chattingFragment == null){
                        chattingFragment = ChattingFragment()
                        fragmentManager.beginTransaction().add(R.id.frame,chattingFragment!!).commit()
                    }
                    if(chattingFragment != null) fragmentManager.beginTransaction().show(chattingFragment!!).commit()
                    if(diaryFragment != null) fragmentManager.beginTransaction().hide(diaryFragment!!).commit()
                    if(settingFragment != null) fragmentManager.beginTransaction().hide(settingFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.diary ->{
                    if(diaryFragment == null){
                        diaryFragment = DiaryFragment()
                        fragmentManager.beginTransaction().add(R.id.frame,diaryFragment!!).commit()
                    }
                    if(chattingFragment != null) fragmentManager.beginTransaction().hide(chattingFragment!!).commit()
                    if(diaryFragment != null) fragmentManager.beginTransaction().show(diaryFragment!!).commit()
                    if(settingFragment != null) fragmentManager.beginTransaction().hide(settingFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.setting ->{
                    if(settingFragment == null){
                        settingFragment = SettingFragment()
                        fragmentManager.beginTransaction().add(R.id.frame,settingFragment!!).commit()
                    }
                    if(chattingFragment != null) fragmentManager.beginTransaction().hide(chattingFragment!!).commit()
                    if(diaryFragment != null) fragmentManager.beginTransaction().hide(diaryFragment!!).commit()
                    if(settingFragment != null) fragmentManager.beginTransaction().show(settingFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}