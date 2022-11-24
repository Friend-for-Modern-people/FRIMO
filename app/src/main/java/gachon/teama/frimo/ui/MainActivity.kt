package gachon.teama.frimo.ui

import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {

        // 최초 실행시 보이는 fragment
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()

        initNavigationBar()

    }

    private fun initNavigationBar() {
        binding.navigationbar.run {
            setOnItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.home -> { // Home

                        // Fragment 변경
                        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()

                        // 아이콘 변경
//                        menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_select)
//                        menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_unselect)
//                        menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_unselect)
                    }
                    R.id.diary -> { // Diary

                        // Fragment 변경
                        supportFragmentManager.beginTransaction().replace(R.id.frame, DiaryFragment()).commit()

                        // 아이콘 변경
//                        menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_unselect)
//                        menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_select)
//                        menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_unselect)
                    }
                    else -> { // Setting

                        // Fragment 변경
                        supportFragmentManager.beginTransaction().replace(R.id.frame, SettingFragment()).commit()

                        // 아이콘 변경
//                        menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_unselect)
//                        menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_unselect)
//                        menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_select) // 선택한 이미지 변경
                    }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }
}