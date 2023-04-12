package gachon.teama.frimo.ui

import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        setFragment(HomeFragment()) // 최초 실행시 보이는 fragment 설정
        initNavigationBar()
    }

    private fun initNavigationBar() = with(binding.navigationbar) {
        setOnItemSelectedListener { item ->
            // 모든 아이콘을 선택되지 않은 것으로 설정
            menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_unselect)
            menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_unselect)
            menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_unselect)

            // 선택된 아이템에 따라 fragment 전환 및 아이콘 변경
            when (item.itemId) {
                R.id.home -> setFragment(HomeFragment())
                R.id.diary -> setFragment(DiaryFragment())
                R.id.setting -> setFragment(SettingFragment())
            }

            // 선택된 아이템에 따라 아이콘 변경
            menu.findItem(item.itemId).setIcon(getSelectedIcon(item.itemId))

            true
        }
        selectedItemId = R.id.home // 초기 선택 아이템
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }

    private fun getSelectedIcon(itemId: Int): Int {
        return when (itemId) {
            R.id.home -> R.drawable.ic_menu_home_select
            R.id.diary -> R.drawable.ic_menu_diary_select
            R.id.setting -> R.drawable.ic_menu_setting_select
            else -> R.drawable.ic_menu_home_unselect
        }
    }
}
