package gachon.teama.frimo.ui

import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setFragment(R.id.frame, HomeFragment()) // 최초 실행시 보이는 fragment 설정
        initNavigationBar()
    }

    /**
     * @description - Navigation bar 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun initNavigationBar() = with(binding) {
        navigationbar.run {
            setOnItemSelectedListener{ item ->
                menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_unselect)
                menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_unselect)
                menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_unselect)

                when (item.itemId) {
                    R.id.home -> { // Home
                        setFragment(R.id.frame, HomeFragment())
                        menu.findItem(R.id.home).setIcon(R.drawable.ic_menu_home_select) // 아이콘 변경
                    }
                    R.id.diary -> { // Diary
                        setFragment(R.id.frame, DiaryFragment())
                        menu.findItem(R.id.diary).setIcon(R.drawable.ic_menu_diary_select) // 아이콘 변경
                    }
                    else -> { // Setting
                        setFragment(R.id.frame, SettingFragment())
                        menu.findItem(R.id.setting).setIcon(R.drawable.ic_menu_setting_select) // 아이콘 변경
                    }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }

    /**
    * @description - Fragment 전환
    * @param - containerViewId(Int) : 띄워질 fragment의 id
    * @param - fragment : 띄워질 fragment
    * @return - None
    * @author - namsh1125
    */
    private fun setFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
    }
}