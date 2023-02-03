package gachon.teama.frimo.ui

import gachon.teama.frimo.adapter.FriendsAdapter
import gachon.teama.frimo.base.BaseActivity
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.databinding.ActivityMyBestFriendBinding

class MyBestFriendActivity : BaseActivity<ActivityMyBestFriendBinding>(ActivityMyBestFriendBinding::inflate) {

    // 좋아요 누른 친구 리스트
    private val likeFriend by lazy {
        AppDatabase.getInstance(this)!!.friendDao().getFriendList().filter { it.like }
    }

    /**
     * @description - Binding 이후
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    override fun initAfterBinding() {
        setScreen()
        setClickListener()
    }

    /**
     * @description - 화면 셋팅
     * @see gachon.teama.frimo.adapter.FriendsAdapter
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() = with(binding) {
        textviewBestFriendCount.text = likeFriend.size.toString() // 좋아요 누른 친구 숫자 설정

        // Set RecyclerView
        recyclerviewMyBestFriend.setHasFixedSize(true)
        recyclerviewMyBestFriend.adapter = FriendsAdapter(likeFriend)
    }

    /**
     * @description - Set click listener
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setClickListener() = with(binding) {
        buttonBack.setOnClickListener { finish() }
    }

}