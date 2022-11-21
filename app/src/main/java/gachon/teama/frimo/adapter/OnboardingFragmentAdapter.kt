package gachon.teama.frimo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import gachon.teama.frimo.ui.OnboardingFragment1
import gachon.teama.frimo.ui.OnboardingFragment2


class OnboardingFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    // ViewPager2에 연결할 Fragment 들을 생성
    private val fragmentList = listOf(OnboardingFragment1(), OnboardingFragment2())

    // ViewPager2에서 노출시킬 Fragment 의 갯수 설정
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // ViewPager2의 각 페이지에서 노출할 Fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}